<h1 align="center">TaxFree Android UI Components</h1>

<p align="center">
  
  <a href="https://jitpack.io/#UTU-Global/Mobile.Android.UI"> <img src="https://jitpack.io/v/UTU-Global/Mobile.Android.UI.svg" /></a>

A component library that can use the custom views for all Tax Free related applications
</p>

## How to integrate into your app?
Integrating the project is simple a refined all you need to do is follow the below steps

Step 1. Add the JitPack repository to your build file. Add it in your root build.gradle at the end of repositories:

```java
allprojects {
  repositories {
    ...
    maven { url "https://jitpack.io" }
  }
}
```
Step 2. Add the dependency
```java
dependencies {
      implementation 'com.github.UTU-Global:Mobile.Android.UI:<latest-version>'
}
```

## How to use this component library?
Okay seems like you integrated the library in your project but **how do you use it**? 

## AtomicButton
Well its really easy just add the following to your xml design

``` xml
.....
 <global.ututaxfree.taxfreeandroidui.AtomicButton
     android:id="@+id/button"
     android:layout_width="match_parent"
     android:layout_height="wrap_content"
     app:disabled="true | false"
     app:size="big | regular | small" />
.....
```
And, you can always change the values from your codebase too
``` kotlin
..... 
button.setDisabled(false) --> To enable the button
.....
``` 

## AtomicTextInput (EditText)
``` xml
.....
 <global.ututaxfree.taxfreeandroidui.AtomicTextInput
     android:layout_width="match_parent"
     android:layout_height="wrap_content"/>
.....
```
## AtomicDialog 
Dialog with the TaxFree components UI. 
The isDeleteUI boolean value will change the UI of the dialog to the Delete component UI

``` kotlin
AtomicDialog("title",
"message",
"positiveButtonText",
"negativeButtonText",
listenerForClickActions,
isDeleteUI(boolean))
.show(supportFragmentManager, "Tag")

```
and the listener will be,
``` kotlin
object : AtomicDialog.OnDialogButtonClickListener {
         override fun onButtonClick(isPositive: Boolean) {
                    if (isPositive) {
                        // code after user tap on Positive button
                        // On tap on negative button, dialog will close
                      }
        }
  }
  ```

## AtomicToast
The TaxFree component Toast

``` kotlin
AtomicToast(
   context, view,
   "message",
   ToastType, listener
 ).show()
 
 ToastType will have three types
 AtomicToast.TYPE_SUCCESS |  AtomicToast.TYPE_WARNING |  AtomicToast.TYPE_ERROR
 
 listner is the ToastClosedListener
   object : ToastClosedListener {
           override fun onToastClosed() {
                 // Do whatever
          }
  })
  
  ```
