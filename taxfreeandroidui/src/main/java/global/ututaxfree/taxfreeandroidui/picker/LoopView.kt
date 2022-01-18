package global.ututaxfree.taxfreeandroidui.picker

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.os.Handler
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.content.res.ResourcesCompat
import global.ututaxfree.taxfreeandroidui.R
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

/**
 * Created by likhitha on 2021/4/8.
 */
class LoopView : View {

    private var scaleX1 = 1.05f
    var lastScrollState = SCROLL_STATE_IDLE
    var currentScrollState = SCROLL_STATE_SETTING

    enum class ACTION {
        CLICK, FLING, DRAG
    }

    var context1: Context? = null
    var handler1: Handler? = null
    private var flingGestureDetector: GestureDetector? = null
    var onItemSelectedListener: OnItemSelectedListener? = null
    var mOnItemScrollListener: OnItemScrollListener? = null

    // Timer mTimer;
    var mExecutor = Executors.newSingleThreadScheduledExecutor()
    private var mFuture: ScheduledFuture<*>? = null
    private var paintOuterText: Paint? = null
    private var paintCenterText: Paint? = null
    private var paintIndicator: Paint? = null
    var items: List<IndexString>? = null
    var textSize = 0
    var itemTextHeight = 0
    var textHeight = 0
    var outerTextColor = 0
    var centerTextColor = 0
    private var dividerColor = 0
    var lineSpacingMultiplier = 0f
    var isLoop = false
    var firstLineY = 0
    var secondLineY = 0
    var totalScrollY = 0
    var initPosition = 0
    var selectedItem = 0
    var change = 0
    var itemsVisibleCount = 0
    var drawingStrings: HashMap<Int, IndexString>? = null

    //    HashMap<String,Integer> drawingStr
    var measuredHeight1 = 0
    var measuredWidth1 = 0
    var halfCircumference = 0
    var radius = 0
    private var mOffset = 0
    private var previousY = 0f
    var startTime: Long = 0
    private val tempRect = Rect()
    private var paddingstart = 0
    private var paddingend = 0
    private var typeface = Typeface.MONOSPACE
    private var paddingTopBottom = 0


    /**
     * set text line space, must more than 1
     * @param lineSpacingMultiplier
     */
    fun setLineSpacing(lineSpacingMultiplier: Float) {
        if (lineSpacingMultiplier > 1.0f) {
            this.lineSpacingMultiplier = lineSpacingMultiplier
        }
    }

    /**
     * set outer text color
     * @param centerTextColor
     */
    fun setMiddleTextColor(centerTextColor: Int) {
        this.centerTextColor = centerTextColor
        if (paintCenterText != null) {
            paintCenterText!!.color = centerTextColor
        }
    }

    /**
     * set center text color
     * @param outerTextColor
     */
    fun setOutTextColor(outerTextColor: Int) {
        this.outerTextColor = outerTextColor
        if (paintOuterText != null) {
            paintOuterText!!.color = outerTextColor
        }
    }

    /**
     * set divider color
     * @param dividerColor
     */
    fun setDividerColor(dividerColor: Int) {
        this.dividerColor = dividerColor
        if (paintIndicator != null) {
            paintIndicator!!.color = dividerColor
        }
    }

    /**
     * set text typeface
     * @param typeface
     */
    fun setTypeface(typeface: Typeface) {
        this.typeface = typeface
    }

    constructor(context: Context) : super(context) {
        initLoopView(context, null)
    }

    constructor(context: Context, attributeset: AttributeSet?) : super(context, attributeset) {
        initLoopView(context, attributeset)
    }

    constructor(context: Context, attributeset: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attributeset,
        defStyleAttr
    ) {
        initLoopView(context, attributeset)
    }

    private fun sp2px(context: Context, spValue: Float): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }

    private fun initLoopView(context: Context, attributeset: AttributeSet?) {
        this.context1 = context
        handler1 = MessageHandler(this)
        flingGestureDetector = GestureDetector(context, LoopViewGestureListener(this))
        flingGestureDetector!!.setIsLongpressEnabled(false)
        val typedArray = context.obtainStyledAttributes(attributeset, R.styleable.LoopView)
        textSize = typedArray.getDimensionPixelSize(
            R.styleable.LoopView_textsize,
            sp2px(this.context, 16.0f)
        )

        setPadding(28, 0, 28, 0)
        lineSpacingMultiplier = 1.0f
        centerTextColor =
            typedArray.getColor(R.styleable.LoopView_centertextColor, -13553359)
        outerTextColor =
            typedArray.getColor(R.styleable.LoopView_outertextColor, -5263441)
        dividerColor =
            typedArray.getColor(R.styleable.LoopView_dividerTextColor, -3815995)
        initPosition = typedArray.getInt(R.styleable.LoopView_initialPosition, -1)
        itemsVisibleCount = typedArray.getInteger(
            R.styleable.LoopView_itemsVisibleCount,
            DEFAULT_VISIBIE_ITEMS
        )
        if (itemsVisibleCount % 2 == 0) {
            itemsVisibleCount = DEFAULT_VISIBIE_ITEMS
        }
        isLoop = typedArray.getBoolean(R.styleable.LoopView_isLoop, true)
        typedArray.recycle()
        drawingStrings = HashMap()
        totalScrollY = 0
        initPosition = -1
    }

    /**
     * visible item count, must be odd number
     *
     * @param visibleNumber
     */
    fun setItemsVisbleCount(visibleNumber: Int) {
        if (visibleNumber % 2 == 0) {
            return
        }
        if (visibleNumber != itemsVisibleCount) {
            itemsVisibleCount = visibleNumber
            drawingStrings = HashMap()
        }
    }

    private fun initPaintsIfPossible() {
        if (paintOuterText == null) {
            paintOuterText = Paint()
            paintOuterText!!.color = outerTextColor
            paintOuterText!!.isAntiAlias = true
            paintOuterText!!.typeface = ResourcesCompat.getFont(
                context,
                R.font.notosans_regular
            )
            paintOuterText!!.textSize = textSize.toFloat()
        }
        if (paintCenterText == null) {
            paintCenterText = Paint()
            paintCenterText!!.color = centerTextColor
            paintCenterText!!.isAntiAlias = true
            paintCenterText!!.typeface = ResourcesCompat.getFont(
                context,
                R.font.notosans_bold
            )
            paintCenterText!!.textSize = textSize.toFloat()
        }
        if (paintIndicator == null) {
            paintIndicator = Paint()
            paintIndicator!!.color = dividerColor
            paintIndicator!!.isAntiAlias = true
            paintIndicator!!.typeface = ResourcesCompat.getFont(
                context,
                R.font.notosans_bold
            )
        }
    }

    private fun remeasure() {
        if (items == null || items!!.isEmpty()) {
            return
        }
        measuredWidth1 = measuredWidth
        measuredHeight1 = measuredHeight
        if (measuredWidth1 == 0 || measuredHeight1 == 0) {
            return
        }
        paddingstart = paddingLeft
        paddingend = paddingRight
        measuredWidth1 -= paddingend
        paintCenterText!!.getTextBounds("\u661F\u671F", 0, 2, tempRect) // 星期
        textHeight = tempRect.height()
        halfCircumference = (measuredHeight1 * Math.PI / 2).toInt()
        itemTextHeight =
            (halfCircumference / (lineSpacingMultiplier * (itemsVisibleCount - 1))).toInt()
        radius = measuredHeight1 / 2
        firstLineY = ((measuredHeight1 - lineSpacingMultiplier * itemTextHeight) / 2.0f).toInt()
        secondLineY = ((measuredHeight1 + lineSpacingMultiplier * itemTextHeight) / 2.0f).toInt()
        if (initPosition == -1) {
            initPosition = if (isLoop) {
                (items!!.size + 1) / 2
            } else {
                0
            }
        }
        selectedItem = initPosition
    }

    fun smoothScroll(action: ACTION) {
        cancelFuture()
        if (action == ACTION.FLING || action == ACTION.DRAG) {
            val itemHeight = lineSpacingMultiplier * itemTextHeight
            mOffset = ((totalScrollY % itemHeight + itemHeight) % itemHeight).toInt()
            mOffset = if (mOffset.toFloat() > itemHeight / 2.0f) {
                (itemHeight - mOffset.toFloat()).toInt()
            } else {
                -mOffset
            }
        }
        mFuture = mExecutor.scheduleWithFixedDelay(
            SmoothScrollTimerTask(this, mOffset),
            0,
            10,
            TimeUnit.MILLISECONDS
        )
        changeScrollState(SCROLL_STATE_SCROLLING)
    }

    fun scrollBy(velocityY: Float) {
        cancelFuture()
        // change this number, can change fling speed
        val velocityFling = 10
        mFuture = mExecutor.scheduleWithFixedDelay(
            InertiaTimerTask(this, velocityY), 0, velocityFling.toLong(),
            TimeUnit.MILLISECONDS
        )
        changeScrollState(SCROLL_STATE_DRAGGING)
    }

    fun cancelFuture() {
        if (mFuture != null && !mFuture!!.isCancelled) {
            mFuture!!.cancel(true)
            mFuture = null
            changeScrollState(SCROLL_STATE_IDLE)
        }
    }


    private fun printMethodStackTrace(methodName: String) {
        val invokers = Thread.currentThread().stackTrace
        val sb = StringBuilder("printMethodStackTrace ")
        sb.append(methodName)
        sb.append(" ")
        for (i in invokers.size - 1 downTo 4) {
            val invoker = invokers[i]
            sb.append(
                String.format(
                    "%s(%d).%s",
                    invoker.fileName,
                    invoker.lineNumber,
                    invoker.methodName
                )
            )
            if (i > 4) {
                sb.append("-->")
            }
        }
        Log.i("printMethodStackTrace", sb.toString())
    }

    private fun changeScrollState(scrollState: Int) {
        if (scrollState != currentScrollState && !handler1!!.hasMessages(MessageHandler.WHAT_SMOOTH_SCROLL_INERTIA)) {
            lastScrollState = currentScrollState
            currentScrollState = scrollState
            //            if(scrollState == SCROLL_STATE_SCROLLING || scrollState == SCROLL_STATE_IDLE){
//                printMethodStackTrace("changeScrollState");
//            }
        }
    }

    /**
     * set not loop
     */
    fun setNotLoop() {
        isLoop = false
    }

    /**
     * set text size in dp
     * @param size
     */
    fun setTextSize(size: Float) {
        if (size > 0.0f) {
            textSize = (context1!!.resources.displayMetrics.density * size).toInt()
            if (paintOuterText != null) {
                paintOuterText!!.textSize = textSize.toFloat()
            }
            if (paintCenterText != null) {
                paintCenterText!!.textSize = textSize.toFloat()
            }
        }
    }

    @JvmName("setInitPosition1")
    fun setInitPosition(initPosition: Int) {
        if (initPosition < 0) {
            this.initPosition = 0
        } else {
            if (items != null && items!!.size > initPosition) {
                this.initPosition = initPosition
            }
        }
    }

    fun setListener(OnItemSelectedListener: OnItemSelectedListener?) {
        onItemSelectedListener = OnItemSelectedListener
    }

    fun setOnItemScrollListener(mOnItemScrollListener: OnItemScrollListener?) {
        this.mOnItemScrollListener = mOnItemScrollListener
    }

    @JvmName("setItems1")
    fun setItems(items: List<String>) {
        this.items = convertData(items)
        remeasure()
        invalidate()
    }

    fun convertData(items: List<String>): List<IndexString> {
        val data: MutableList<IndexString> = ArrayList()
        for (i in items.indices) {
            data.add(IndexString(i, items[i]))
        }
        return data
    }

    //
    // protected final void scrollBy(float velocityY) {
    // Timer timer = new Timer();
    // mTimer = timer;
    // timer.schedule(new InertiaTimerTask(this, velocityY, timer), 0L, 20L);
    // }
    fun onItemSelected() {
        if (onItemSelectedListener != null) {
            postDelayed(OnItemSelectedRunnable(this), 200L)
        }
    }

    /**
     * link https://github.com/weidongjian/androidWheelView/issues/10
     *
     * @param scaleX
     */
    override fun setScaleX(scaleX: Float) {
        this.scaleX1 = scaleX
    }

    /**
     * set current item position
     * @param position
     */
    fun setCurrentPosition(position: Int) {
        if (items == null || items!!.isEmpty()) {
            return
        }
        val size = items!!.size
        if (position >= 0 && position < size && position != selectedItem) {
            initPosition = position
            totalScrollY = 0
            mOffset = 0
            changeScrollState(SCROLL_STATE_SETTING)
            invalidate()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (items == null || items!!.isEmpty()) {
            return
        }
        change = (totalScrollY / (lineSpacingMultiplier * itemTextHeight)).toInt()
        selectedItem = initPosition + change % items!!.size
        if (!isLoop) {
            if (selectedItem < 0) {
                selectedItem = 0
            }
            if (selectedItem > items!!.size - 1) {
                selectedItem = items!!.size - 1
            }
        } else {
            if (selectedItem < 0) {
                selectedItem = items!!.size + selectedItem
            }
            if (selectedItem > items!!.size - 1) {
                selectedItem = selectedItem - items!!.size
            }
        }
        val j2 = (totalScrollY % (lineSpacingMultiplier * itemTextHeight)).toInt()
        // put value to drawingString
        var k1 = 0
        while (k1 < itemsVisibleCount) {
            var l1 = selectedItem - (itemsVisibleCount / 2 - k1)
            if (isLoop) {
                while (l1 < 0) {
                    l1 = l1 + items!!.size
                }
                while (l1 > items!!.size - 1) {
                    l1 = l1 - items!!.size
                }
                drawingStrings!![k1] = items!![l1]
            } else if (l1 < 0) {
//                drawingStrings[k1] = "";
                drawingStrings!![k1] = IndexString()
            } else if (l1 > items!!.size - 1) {
//                drawingStrings[k1] = "";
                drawingStrings!![k1] = IndexString()
            } else {
                // drawingStrings[k1] = items.get(l1);
                drawingStrings!![k1] = items!![l1]
            }
            k1++
        }
        canvas.drawLine(
            paddingstart.toFloat(),
            firstLineY.toFloat(),
            measuredWidth1.toFloat(),
            firstLineY.toFloat(),
            paintIndicator!!
        )
        canvas.drawLine(
            paddingstart.toFloat(),
            secondLineY.toFloat(),
            measuredWidth1.toFloat(),
            secondLineY.toFloat(),
            paintIndicator!!
        )
        var i = 0
        while (i < itemsVisibleCount) {
            canvas.save()
            val itemHeight = itemTextHeight * lineSpacingMultiplier
            val radian = (itemHeight * i - j2) * Math.PI / halfCircumference
            if (radian >= Math.PI || radian <= 0) {
                canvas.restore()
            } else {
                val translateY =
                    (radius - Math.cos(radian) * radius - Math.sin(radian) * itemTextHeight / 2.0).toInt()
                canvas.translate(0.0f, translateY.toFloat())
                canvas.scale(1.0f, Math.sin(radian).toFloat())
                if (translateY <= firstLineY && itemTextHeight + translateY >= firstLineY) {
                    // first divider
                    canvas.save()
                    canvas.clipRect(30, 0, measuredWidth1, firstLineY - translateY)
                    drawOuterText(canvas, i)
                    canvas.restore()
                    canvas.save()
                    canvas.clipRect(30, firstLineY - translateY, measuredWidth1, itemHeight.toInt())
                    drawCenterText(canvas, i)
                    canvas.restore()
                } else if (translateY <= secondLineY && itemTextHeight + translateY >= secondLineY) {
                    // second divider
                    canvas.save()
                    canvas.clipRect(30, 0, measuredWidth1, secondLineY - translateY)
                    drawCenterText(canvas, i)
                    canvas.restore()
                    canvas.save()
                    canvas.clipRect(30, secondLineY - translateY, measuredWidth1, itemHeight.toInt())
                    drawOuterText(canvas, i)
                    canvas.restore()
                } else if (translateY >= firstLineY && itemTextHeight + translateY <= secondLineY) {
                    // center item
                    canvas.clipRect(30, 0, measuredWidth1, itemHeight.toInt())
                    drawCenterText(canvas, i)
                } else {
                    // other item
                    canvas.clipRect(0, 0, measuredWidth1, itemHeight.toInt())
                    drawOuterText(canvas, i)
                }
                canvas.restore()
            }
            i++
        }
        if (currentScrollState != lastScrollState) {
            val oldScrollState = lastScrollState
            lastScrollState = currentScrollState
            if (mOnItemScrollListener != null) {
                mOnItemScrollListener!!.onItemScrollStateChanged(
                    this,
                    selectedItem,
                    oldScrollState,
                    currentScrollState,
                    totalScrollY
                )
            }
        }
        if (currentScrollState == SCROLL_STATE_DRAGGING || currentScrollState == SCROLL_STATE_SCROLLING) {
            if (mOnItemScrollListener != null) {
                mOnItemScrollListener!!.onItemScrolling(
                    this,
                    selectedItem,
                    currentScrollState,
                    totalScrollY
                )
            }
        }
    }

    private fun drawOuterText(canvas: Canvas, position: Int) {
        canvas.drawText(
            drawingStrings!![position]!!.string,
            getTextX(drawingStrings!![position]!!.string, paintOuterText, tempRect).toFloat(),
            drawingY.toFloat(),
            paintOuterText!!
        )
    }

    private fun drawCenterText(canvas: Canvas, position: Int) {
        canvas.drawText(
            drawingStrings!![position]!!.string,
            getTextX(drawingStrings!![position]!!.string, paintCenterText, tempRect).toFloat(),
            drawingY.toFloat(),
            paintCenterText!!
        )
    }

    private val drawingY: Int
        private get() = if (itemTextHeight > textHeight) {
            itemTextHeight - (itemTextHeight - textHeight) / 2
        } else {
            itemTextHeight
        }

    // text start drawing position
    private fun getTextX(a: String, paint: Paint?, rect: Rect): Int {
        paint!!.getTextBounds(a, 0, a.length, rect)
        var textWidth = rect.width()
        textWidth *= scaleX1.toInt()
        return (measuredWidth1 - paddingstart - textWidth) / 2 + paddingstart
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        initPaintsIfPossible()
        remeasure()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val eventConsumed = flingGestureDetector!!.onTouchEvent(event)
        val itemHeight = lineSpacingMultiplier * itemTextHeight
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                startTime = System.currentTimeMillis()
                cancelFuture()
                previousY = event.rawY
                if (parent != null) {
                    parent.requestDisallowInterceptTouchEvent(true)
                }
            }
            MotionEvent.ACTION_MOVE -> {
                val dy = previousY - event.rawY
                previousY = event.rawY
                totalScrollY = (totalScrollY + dy).toInt()
                if (!isLoop) {
                    val top = -initPosition * itemHeight
                    val bottom = (items!!.size - 1 - initPosition) * itemHeight
                    if (totalScrollY < top) {
                        totalScrollY = top.toInt()
                    } else if (totalScrollY > bottom) {
                        totalScrollY = bottom.toInt()
                    }
                }
                changeScrollState(SCROLL_STATE_DRAGGING)
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                if (!eventConsumed) {
                    val y = event.y
                    val l = Math.acos(((radius - y) / radius).toDouble()) * radius
                    val circlePosition = ((l + itemHeight / 2) / itemHeight).toInt()
                    val extraOffset = (totalScrollY % itemHeight + itemHeight) % itemHeight
                    mOffset =
                        ((circlePosition - itemsVisibleCount / 2) * itemHeight - extraOffset).toInt()
                    if (System.currentTimeMillis() - startTime > 120) {
                        smoothScroll(ACTION.DRAG)
                    } else {
                        smoothScroll(ACTION.CLICK)
                    }
                }
                if (parent != null) {
                    parent.requestDisallowInterceptTouchEvent(false)
                }
            }
            else -> {
                if (!eventConsumed) {
                    val y = event.y
                    val l = Math.acos(((radius - y) / radius).toDouble()) * radius
                    val circlePosition = ((l + itemHeight / 2) / itemHeight).toInt()
                    val extraOffset = (totalScrollY % itemHeight + itemHeight) % itemHeight
                    mOffset =
                        ((circlePosition - itemsVisibleCount / 2) * itemHeight - extraOffset).toInt()
                    if (System.currentTimeMillis() - startTime > 120) {
                        smoothScroll(ACTION.DRAG)
                    } else {
                        smoothScroll(ACTION.CLICK)
                    }
                }
                if (parent != null) {
                    parent.requestDisallowInterceptTouchEvent(false)
                }
            }
        }
        invalidate()
        return true
    }

    inner class IndexString {
        constructor() {
            string = ""
        }

        constructor(index: Int, str: String) {
            this.index = index
            string = str
        }

        var string: String
        private var index = 0
    }

    companion object {
        private const val DEFAULT_VISIBIE_ITEMS = 9
        const val SCROLL_STATE_IDLE = 0
        const val SCROLL_STATE_SETTING = 1
        const val SCROLL_STATE_DRAGGING = 2
        const val SCROLL_STATE_SCROLLING = 3
    }
}