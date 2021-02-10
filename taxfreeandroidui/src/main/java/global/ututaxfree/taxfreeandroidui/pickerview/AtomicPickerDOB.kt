package global.ututaxfree.taxfreeandroidui.pickerview

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.os.Handler
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.content.res.ResourcesCompat
import global.ututaxfree.taxfreeandroidui.R
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit
import kotlin.math.abs

class AtomicPickerDOB : View {
    private val executorService: ScheduledExecutorService
    private var scheduledFuture: ScheduledFuture<*>? = null
    private var loopScrollListener: LoopScrollListener? = null
    private val onGestureListener: GestureDetector.SimpleOnGestureListener
    private var gestureDetector: GestureDetector? = null
    private val topBottomTextPaint: Paint
    private val centerTextPaint: Paint
    private val centerLinePaint: Paint
    private var data: List<String>? = null
    private var itemCount: Array<String?>? = null
    private var lineSpacingMultiplier = 0f
    private var itemHeight = 0f
    private var canLoop = false
    private var totalScrollY = 0
    var selectedItem = 0
        private set
    private var textSize = 0
    private var maxTextWidth = 0
    private var maxTextHeight = 0
    private var topBottomTextColor = 0
    private var centerTextColor = 0
    private var centerLineColor = 0
    private var topLineY = 0
    private var bottomLineY = 0
    private var currentIndex = 0
    private var initialPosition = 0
    private var paddingLeftRight = 0
    private var paddingTopBottom = 0
    private var drawItemsCount = 0
    private var circularDiameter = 0
    private var widgetHeight = 0
    private var circularRadius = 0
    private var widgetWidth = 0
    private var localHandler: Handler

    @JvmOverloads
    constructor(
        context: Context?,
        attrs: AttributeSet? = null as AttributeSet?,
        defStyleAttr: Int = 0
    ) : super(context, attrs, defStyleAttr) {
        executorService = Executors.newSingleThreadScheduledExecutor()
        onGestureListener = WheelViewGestureListener()
        topBottomTextPaint = Paint()
        centerTextPaint = Paint()
        centerLinePaint = Paint()
        localHandler = Handler(`AtomicPickerDOB$$Lambda$1`.`lambdaFactory$`(this))
        if (!this.isInEditMode) {
            initView(attrs!!)
        }
    }

    @TargetApi(21)
    constructor(
        context: Context?,
        attrs: AttributeSet,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        executorService = Executors.newSingleThreadScheduledExecutor()
        onGestureListener = WheelViewGestureListener()
        topBottomTextPaint = Paint()
        centerTextPaint = Paint()
        centerLinePaint = Paint()
        localHandler = Handler(`AtomicPickerDOB$$Lambda$2`.`lambdaFactory$`(this))
        if (!this.isInEditMode) {
            initView(attrs)
        }
    }

    private fun initView(attrs: AttributeSet) {
        val array =
            this.context.obtainStyledAttributes(attrs, R.styleable.AtomicPicker)
        try {
            topBottomTextColor =
                array.getColor(R.styleable.AtomicPicker_topBottomTextColor, -5263441)
            centerTextColor =
                array.getColor(R.styleable.AtomicPicker_centerTextColor, -13553359)
            centerLineColor =
                array.getColor(R.styleable.AtomicPicker_lineColor, -3815995)
            canLoop = array.getBoolean(R.styleable.AtomicPicker_canLoop, true)
            initialPosition = array.getInt(R.styleable.AtomicPicker_initPosition, -1)
            textSize = array.getDimensionPixelSize(
                R.styleable.AtomicPicker_textSize,
                sp2px(this.context, 16.0f)
            )
            drawItemsCount = array.getInt(R.styleable.AtomicPicker_drawItemCount, 7)
        } finally {
            array.recycle()
        }
        lineSpacingMultiplier = 2.0f
        setLayerType(1, null as Paint?)

        itemCount = arrayOfNulls(drawItemsCount)
        gestureDetector = GestureDetector(this.context, onGestureListener)
        gestureDetector!!.setIsLongpressEnabled(false)
    }

    private fun initData() {
        requireNotNull(data) { "data list must not be null!" }
        topBottomTextPaint.color = topBottomTextColor
        topBottomTextPaint.isAntiAlias = true
        topBottomTextPaint.typeface = ResourcesCompat.getFont(
            context,
            R.font.notosans_bold
        )
        topBottomTextPaint.textSize = textSize.toFloat()
        centerTextPaint.color = centerTextColor
        centerTextPaint.isAntiAlias = true
        centerTextPaint.textScaleX = 1.05f
        centerTextPaint.typeface = ResourcesCompat.getFont(
            context,
            R.font.notosans_bold
        )
        centerTextPaint.textSize = textSize.toFloat()
        centerLinePaint.color = centerLineColor
        centerLinePaint.isAntiAlias = true
        centerLinePaint.typeface = ResourcesCompat.getFont(
            context,
            R.font.notosans_bold
        )
        centerLinePaint.textSize = textSize.toFloat()
        measureTextWidthHeight()
        val mHalfCircumference =
            (maxTextHeight.toFloat() * lineSpacingMultiplier * (drawItemsCount - 1).toFloat()).toInt()
        circularDiameter =
            ((mHalfCircumference * 2).toDouble() / 3.141592653589793).toInt()
        circularRadius = (mHalfCircumference.toDouble() / 3.141592653589793).toInt()
        if (initialPosition == -1) {
            initialPosition = if (canLoop) (data!!.size + 1) / 2 else 0
        }
        currentIndex = initialPosition
        this.invalidate()
    }

    private fun measureTextWidthHeight() {
        val rect = Rect()
        for (i in data!!.indices) {
            val s1 = data!![i]
            centerTextPaint.getTextBounds(s1, 0, s1.length, rect)
            val textWidth = rect.width()
            val textHeight = rect.height()
            maxTextWidth = if (textWidth > maxTextWidth) textWidth else maxTextWidth
            maxTextHeight =
                if (textHeight > maxTextHeight) textHeight else maxTextHeight
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        widgetWidth = this.measuredWidth
        widgetHeight = MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        Log.i(TAG, "onMeasure -> heightMode:$heightMode")
        itemHeight = lineSpacingMultiplier * maxTextHeight.toFloat()
        paddingLeftRight = (widgetWidth - maxTextWidth) / 2
        paddingTopBottom = (widgetHeight - circularDiameter) / 2
        topLineY =
            ((circularDiameter.toFloat() - itemHeight) / 2.0f).toInt() + paddingTopBottom
        bottomLineY =
            ((circularDiameter.toFloat() + itemHeight) / 2.0f).toInt() + paddingTopBottom
    }

    override fun onDraw(canvas: Canvas) {
        if (data == null) {
            super.onDraw(canvas)
        } else {
            super.onDraw(canvas)
            val mChangingItem = (totalScrollY.toFloat() / itemHeight).toInt()
            currentIndex = initialPosition + mChangingItem % data!!.size
            if (!canLoop) {
                currentIndex = if (currentIndex < 0) 0 else currentIndex
                currentIndex =
                    if (currentIndex > data!!.size - 1) data!!.size - 1 else currentIndex
            } else {
                currentIndex =
                    if (currentIndex < 0) currentIndex + data!!.size else currentIndex
                currentIndex =
                    if (currentIndex > data!!.size - 1) currentIndex - data!!.size else currentIndex
            }
            var count: Int
            var templateItem: Int
            count = 0
            while (count < drawItemsCount) {
                templateItem = currentIndex - (drawItemsCount / 2 - count)
                if (canLoop) {
                    templateItem =
                        if (templateItem < 0) templateItem + data!!.size else templateItem
                    templateItem =
                        if (templateItem > data!!.size - 1) templateItem - data!!.size else templateItem
                    itemCount!![count] = data!![templateItem]
                } else if (templateItem >= 0 && templateItem <= data!!.size - 1) {
                    itemCount!![count] = data!![templateItem]
                } else {
                    itemCount!![count] = ""
                }
                ++count
            }
            canvas.drawLine(
                0.0f,
                topLineY.toFloat(),
                widgetWidth.toFloat(),
                topLineY.toFloat(),
                centerLinePaint
            )
            canvas.drawLine(
                0.0f,
                bottomLineY.toFloat(),
                widgetWidth.toFloat(),
                bottomLineY.toFloat(),
                centerLinePaint
            )
            templateItem = (totalScrollY.toFloat() % itemHeight).toInt()
            count = 0
            while (count < drawItemsCount) {
                canvas.save()
                val itemHeight =
                    maxTextHeight.toFloat() * lineSpacingMultiplier
                val radian =
                    ((itemHeight * count.toFloat() - templateItem.toFloat()) / circularRadius.toFloat()).toDouble()
                val angle = (radian * 180.0 / 3.141592653589793).toFloat()
                if (angle < 180.0f && angle > 0.0f) {
                    val translateY =
                        (circularRadius.toDouble() - Math.cos(radian) * circularRadius.toDouble() - Math.sin(
                            radian
                        ) * maxTextHeight.toDouble() / 2.0).toInt() + paddingTopBottom
                    canvas.translate(0.0f, translateY.toFloat())
                    canvas.scale(1.0f, Math.sin(radian).toFloat())
                    if (translateY > topLineY && maxTextHeight + translateY < bottomLineY) {
                        if (translateY >= topLineY && maxTextHeight + translateY <= bottomLineY) {
                            canvas.clipRect(0, 0, widgetWidth, itemHeight.toInt())
                            canvas.drawText(
                                itemCount!![count]!!,
                                paddingLeftRight.toFloat(),
                                maxTextHeight.toFloat(),
                                centerTextPaint
                            )
                            selectedItem = data!!.indexOf(itemCount!![count])
                        }
                    } else {
                        val diff =
                            if (translateY <= topLineY) topLineY - translateY else bottomLineY - translateY
                        val topBottomPaint =
                            if (translateY <= topLineY) topBottomTextPaint else centerTextPaint
                        val centerPaint =
                            if (translateY <= topLineY) centerTextPaint else topBottomTextPaint
                        canvas.save()
                        canvas.clipRect(0, 0, widgetWidth, diff)
                        canvas.drawText(
                            itemCount!![count]!!,
                            paddingLeftRight.toFloat(),
                            maxTextHeight.toFloat(),
                            topBottomPaint
                        )
                        canvas.restore()
                        canvas.save()
                        canvas.clipRect(0, diff, widgetWidth, itemHeight.toInt())
                        canvas.drawText(
                            itemCount!![count]!!,
                            paddingLeftRight.toFloat(),
                            maxTextHeight.toFloat(),
                            centerPaint
                        )
                        canvas.restore()
                    }
                    canvas.restore()
                } else {
                    canvas.restore()
                }
                ++count
            }
        }
    }

    override fun onTouchEvent(motionevent: MotionEvent): Boolean {
        return when (motionevent.action) {
            1 -> {
                if (!gestureDetector!!.onTouchEvent(motionevent)) {
                    this.startSmoothScrollTo()
                }
                true
            }
            else -> {
                if (!gestureDetector!!.onTouchEvent(motionevent)) {
                    this.startSmoothScrollTo()
                }
                true
            }
        }
    }

    fun setCanLoop(canLoop: Boolean) {
        this.canLoop = canLoop
        this.invalidate()
    }

    fun setTextSize(size: Float) {
        if (size > 0.0f) {
            textSize = sp2px(this.context, size)
        }
    }

    fun setInitPosition(initPosition: Int) {
        initialPosition = initPosition
        this.invalidate()
    }

    fun setLoopListener(LoopListener: LoopScrollListener?) {
        loopScrollListener = LoopListener
    }

    fun setItems(list: List<String>?) {
        data = list
        initData()
    }

    private fun itemSelected() {
        if (loopScrollListener != null) {
            postDelayed(`AtomicPickerDOB$$Lambda$3`.`lambdaFactory$`(this), 200L)
        }
    }

    private fun onItemSelected() {
        data!![selectedItem]
        if (null != loopScrollListener) {
            loopScrollListener!!.onItemSelect(selectedItem)
        }
    }

    private fun cancelSchedule() {
        if (null != scheduledFuture && !scheduledFuture!!.isCancelled) {
            scheduledFuture!!.cancel(true)
            scheduledFuture = null
        }
    }

    private fun startSmoothScrollTo() {
        val offset = (totalScrollY.toFloat() % itemHeight).toInt()
        cancelSchedule()
        scheduledFuture = executorService.scheduleWithFixedDelay(
            HalfHeightRunnable(offset),
            0L,
            10L,
            TimeUnit.MILLISECONDS
        )
    }

    private fun startSmoothScrollTo(velocityY: Float) {
        cancelSchedule()
        val velocityFling = 20
        scheduledFuture = executorService.scheduleWithFixedDelay(
            FlingRunnable(velocityY),
            0L,
            velocityFling.toLong(),
            TimeUnit.MILLISECONDS
        )
    }

    fun sp2px(context: Context, spValue: Float): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }

    internal inner class FlingRunnable(val velocityY: Float) : Runnable {
        var velocity = 2.14748365E9f
        override fun run() {
            if (velocity == 2.14748365E9f) {
                if (abs(velocityY) > 2000.0f) {
                    velocity = if (velocityY > 0.0f) 2000.0f else -2000.0f
                } else {
                    velocity = velocityY
                }
            }
            Log.i(TAG, "velocity->" + velocity)
            if (Math.abs(velocity) in 0.0f..20.0f) {
                cancelSchedule()
                localHandler.sendEmptyMessage(2000)
            } else {
                val i = (velocity * 10.0f / 1000.0f).toInt()
                totalScrollY -= i
                if (!canLoop) {
                    val itemHeight =
                        lineSpacingMultiplier * maxTextHeight.toFloat()
                    if (totalScrollY <= ((-initialPosition).toFloat() * itemHeight).toInt()) {
                        velocity = 40.0f
                        totalScrollY =
                            ((-initialPosition).toFloat() * itemHeight).toInt()
                    } else if (totalScrollY >= ((data!!.size - 1 - initialPosition).toFloat() * itemHeight).toInt()) {
                        totalScrollY =
                            ((data!!.size - 1 - initialPosition).toFloat() * itemHeight).toInt()
                        velocity = -40.0f
                    }
                }
                velocity =
                    if (velocity < 0.0f) velocity + 20.0f else velocity - 20.0f
                localHandler.sendEmptyMessage(1000)
            }
        }

    }

    internal inner class HalfHeightRunnable(var offset: Int) : Runnable {
        var realTotalOffset = 2147483647
        var realOffset = 0
        override fun run() {
            if (realTotalOffset == 2147483647) {
                if (offset.toFloat() > itemHeight / 2.0f) {
                    realTotalOffset =
                        (itemHeight - offset.toFloat()).toInt()
                } else {
                    realTotalOffset = -offset
                }
            }
            realOffset = (realTotalOffset.toFloat() * 0.1f).toInt()
            if (realOffset == 0) {
                realOffset = if (realTotalOffset < 0) -1 else 1
            }
            if (Math.abs(realTotalOffset) <= 0) {
                cancelSchedule()
                localHandler.sendEmptyMessage(3000)
            } else {
                totalScrollY += realOffset
                localHandler.sendEmptyMessage(1000)
                realTotalOffset -= realOffset
            }
        }

    }

    internal inner class WheelViewGestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(motionevent: MotionEvent): Boolean {
            cancelSchedule()
            Log.i(TAG, "WheelViewGestureListener -> onDown")
            return true
        }

        override fun onFling(
            e1: MotionEvent,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            this@AtomicPickerDOB.startSmoothScrollTo(velocityY)
            Log.i(TAG, "WheelViewGestureListener -> onFling")
            return true
        }

        override fun onScroll(
            e1: MotionEvent,
            e2: MotionEvent,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            Log.i(
                TAG,
                "WheelViewGestureListener -> onScroll"
            )
            totalScrollY =
                (totalScrollY.toFloat() + distanceY).toInt()
            if (!canLoop) {
                val circleLength =
                    ((data!!.size - 1 - initialPosition).toFloat() * itemHeight).toInt()
                val initPositionCircleLength =
                    (initialPosition.toFloat() * itemHeight).toInt()
                val initPositionStartY = -1 * initPositionCircleLength
                totalScrollY =
                    if (totalScrollY < initPositionStartY) initPositionStartY else totalScrollY
                totalScrollY =
                    if (totalScrollY >= circleLength) circleLength else totalScrollY
            }
            this@AtomicPickerDOB.invalidate()
            return true
        }
    }

    companion object {
        private val TAG = AtomicPicker::class.java.simpleName
    }
}