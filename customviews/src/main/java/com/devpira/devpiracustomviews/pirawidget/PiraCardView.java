package com.devpira.devpiracustomviews.pirawidget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.devpira.devpiracustomviews.R;

/**
 * Created by pira.suriyakumaran on 11/28/2016.
 */

public class PiraCardView extends CardView{
        /**
         * Base dimension for which view is measure relatively to.
         * Default value are phone width and phone height
         */
        private int pWidth, pHeight;


        private int viewWidth, viewHeight;
        private int leftMargin, topMargin, rightMargin, bottomMargin;
        private int leftPadding, topPadding, rightPadding, bottomPadding;

        /**
         * Parent View is a view for which this view bases its measurement off of.
         * It can be a view higher up in the hierarchy or on the same level or null.
         *  ref is the xml ID ref for the parentView
         */
        private View parentView;
        private int ref;

        public PiraCardView(Context context) {
            super(context);

            this.pWidth = getContext().getResources().getDisplayMetrics().widthPixels;
            this.pHeight = getContext().getResources().getDisplayMetrics().heightPixels;
            setCardBackgroundColor(Color.parseColor("#FFFFFF"));
        }

        public PiraCardView(Context context, AttributeSet attrs) {
            super(context, attrs);

            this.pWidth = getContext().getResources().getDisplayMetrics().widthPixels;
            this.pHeight = getContext().getResources().getDisplayMetrics().heightPixels;

            typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.PiraRelativeLayout, 0,0);
            setCardBackgroundColor(Color.parseColor("#FFFFFF"));
        }


        TypedArray typedArray;
        private boolean typedArraySet = false;
        @Override
        protected void onAttachedToWindow() {
            super.onAttachedToWindow();

            if(typedArray == null)   // If typedArray is null then view was programmatically created
                return;             // and not through xml. Therefore no xml attr available to use for below.
            if(typedArraySet)
                return;
            try {
                ref = typedArray.getResourceId(R.styleable.PiraCardView_meParentView,0);

                //Following sets view's Width and Height:
                if (getRootView().findViewById(ref) != null) {      //If this not null then this view's base dimensions are being changed from the
                    parentView = getRootView().findViewById(ref);      // default dimensions of the phone to the parentView view referenced in XML
                    if(parentView.getLayoutParams().width > 0 )
                        pWidth = parentView.getLayoutParams().width;
                    if(parentView.getLayoutParams().height> 0)
                        pHeight = parentView.getLayoutParams().height;
                }

                viewWidth = (int) typedArray.getFraction(R.styleable.PiraCardView_meWidth,pWidth,pHeight,0);
                viewHeight = (int) typedArray.getFraction(R.styleable.PiraCardView_meHeight,pHeight,pWidth,0);
                if(viewWidth > 0 && getLayoutParams() != null)
                    getLayoutParams().width = viewWidth;
                if(viewHeight >  0 && getLayoutParams() != null)
                    getLayoutParams().height = viewHeight;

                //Following positions the view:
                setX(typedArray.getFraction(R.styleable.PiraCardView_meX,pWidth,pHeight,0));
                setY(typedArray.getFraction(R.styleable.PiraCardView_meY,pHeight,pWidth,0));

                //Following sets margins if any:
                int margin = (int) typedArray.getFraction(R.styleable.PiraCardView_meMargin, pWidth,pHeight,0);
                if(margin == 0) {                                                                                               //If margin is set then all
                    leftMargin = (int) typedArray.getFraction(R.styleable.PiraCardView_meLeftMargin, pWidth, pHeight, 0);        // individual margins will be overridden
                    rightMargin = (int) typedArray.getFraction(R.styleable.PiraCardView_meRightMargin, pWidth, pHeight, 0);     // to the margin because margin takes priority
                    topMargin = (int) typedArray.getFraction(R.styleable.PiraCardView_meTopMargin, pHeight, pWidth, 0);
                    bottomMargin = (int) typedArray.getFraction(R.styleable.PiraCardView_meBottomMargin, pHeight, pWidth, 0);
                }else{
                    leftMargin = rightMargin = topMargin = bottomMargin = margin;
                }
                setMargin(leftMargin,topMargin,rightMargin,bottomMargin);

                //Follwing sets padding if any:
                int padding = (int) typedArray.getFraction(R.styleable.PiraCardView_mePadding,pWidth,pHeight,0);
                if(padding == 0) {                                                                                               //If padding is set then all
                    leftPadding = (int) typedArray.getFraction(R.styleable.PiraCardView_meLeftPadding, pWidth, pHeight, 0);        // individual padding will be overridden
                    rightPadding = (int) typedArray.getFraction(R.styleable.PiraCardView_meRightPadding, pWidth, pHeight, 0);     // to the padding because mePadding takes priority
                    topPadding = (int) typedArray.getFraction(R.styleable.PiraCardView_meTopPadding, pHeight, pWidth, 0);
                    bottomPadding = (int) typedArray.getFraction(R.styleable.PiraCardView_meBottomPadding, pHeight, pWidth, 0);
                }else{
                    leftPadding = rightPadding = topPadding = bottomPadding = padding;
                }
                setPadding(leftPadding,topPadding,rightPadding,bottomPadding);


            }finally {
                if(typedArray != null)
                    typedArray.recycle();
                typedArraySet = true;
            }
        }


        /**
         * Change base width from phone width to custom width
         * @param width is the base width for which this view's measurement will be based off of.
         */
        public void meBaseWidth(int width){
            if (width>0)
                pWidth = width;
        }

        public int whatBaseWidth(){
            return pWidth;
        }

        /**
         * Change base height from phone height to custom height:
         * @param height is the base height for which this view's measurement will be based off of.
         */
        public void meBaseHeight(int height){
            if (height>0)
                pHeight = height;
        }

        public int whatBaseHeight(){
            return pHeight;
        }
        /**
         * Change base dimensions from phone width and height to custom width and height
         * @param width is the base width for which this view's measurement will be based off of.
         * @param height is the base width for which this view's measurement will be based off of.
         */
        public void meBaseDimensions(int width, int height){
            if(width >0 )
                pWidth = width;
            if(height >0)
                pHeight = height;
        }


        /**
         * Change width of the view. Value should range between 0 - 100.
         * @param width is the width of the view in percentage of the base
         */
        public void meWidth (float width){
            this.meWidth(width,pWidth);
        }

        /**
         * Change width of the view. Value should range between 0 - 100.
         * @param width is the width of the view in percentage of the base
         * @param base is the value of which a percentage will be taken of. Default set to phone Width.
         */
        public void meWidth (float width, float base){
            if(getLayoutParams() == null)
                return;
            if(width != ViewGroup.LayoutParams.MATCH_PARENT && width != ViewGroup.LayoutParams.WRAP_CONTENT){
                width = width/100 * base;
            }
            getLayoutParams().width = (int) width;
        }

        /**
         * Change height of the view. Value should range between 0 - 100.
         * @param height is the height of the view in percentage of the base
         */
        public void meHeight( float height){
            this.meHeight(height,pHeight);
        }

        /**
         * Change height of the view. Value should range between 0 - 100.
         * @param height is the height of the view in percentage of the base
         * @param base is the value of which a percentage will be taken of. Default set to phone Height.
         */
        public void meHeight(float height, float base){
            if(getLayoutParams() == null)
                return;
            if(height !=  ViewGroup.LayoutParams.MATCH_PARENT && height != ViewGroup.LayoutParams.WRAP_CONTENT){
                height = height/100 *base;
            }
            getLayoutParams().height = (int) height;
        }

        /**
         * Change x position of the view. Value should range between 0 - 100.
         * @param xPercentage is the position of x in percentage of the base.
         */

        public void meX(float xPercentage){
            setX(xPercentage/100 *pWidth);
        }
        /**
         * Change x position of the view. Value should range between 0 - 100.
         * @param xPercentage is the position of x in percentage of the base.
         * @param base is the value of which a percentage will be taken of. Default set to phone width.
         */
        public void meX(float xPercentage, float base){
            setX(xPercentage/100 *base);
        }
        /**
         * Change y position of the view. Value should range between 0 - 100.
         * @param yPercentage is the position of y in percentage of the base.
         */
        public void meY(float yPercentage){
            setY(yPercentage/100*pHeight);
        }
        /**
         * Change y position of the view. Value should range between 0 - 100.
         * @param yPercentage is the position of y in percentage of the base.
         * @param base is the value of which a percentage will be taken of. Default set to phone height.
         */
        public void meY(float yPercentage, float base){
            setY(yPercentage/100*base);
        }

        /**
         * @param left is the margin to the left of the view as a percentage of the base.
         */
        public void meLeftMargin(float left){
            this.setMargin((int) (left/100*pWidth),topMargin,rightMargin,bottomMargin);
        }
        /**
         * @param left is the margin to the left of the view as a percentage of the base.
         * @param base is the value of which a percentage will be taken of. Default set to phone width.
         */
        public void meLeftMargin(float left, float base){
            this.setMargin((int) (left/100*base),topMargin,rightMargin,bottomMargin);
        }
        /**
         * @param top is the margin on top of the view as a percentage of the base.
         */
        public void meTopMargin(float top){
            this.setMargin(leftMargin,(int) (top/100*pHeight),rightMargin,bottomMargin);
        }
        /**
         * @param top is the margin on top of the view as a percentage of the base.
         * @param base  is the value of which a percentage will be taken of. Default set to phone height.
         */
        public void meTopMargin(float top, float base){
            this.setMargin(leftMargin,(int) (top/100*base),rightMargin,bottomMargin);
        }
        /**
         * @param right is the margin to the right of the view as a percentage of the base.
         */
        public void meRightMargin(float right){
            this.setMargin(leftMargin,topMargin,(int) (right/100*pWidth),bottomMargin);
        }
        /**
         * @param right is the margin to the right of the view as a percentage of the base.
         * @param base is the value of which a percentage will be taken of. Default set to phone width.
         */
        public void meRightMargin(float right, float base){
            this.setMargin(leftMargin,topMargin,(int) (right/100*base),bottomMargin);
        }
        /**
         * @param bottom is the margin below the view as a percentage of the base.
         */
        public void meBottomMargin(float bottom){
            this.setMargin(leftMargin,topMargin,rightMargin,(int) (bottom/100*pHeight));
        }
        /**
         * @param bottom is the margin below the view as a percentage of the base.
         * @param base  is the value of which a percentage will be taken of. Default set to phone height.
         */
        public void meBottomMargin(float bottom, float base){
            this.setMargin(leftMargin,topMargin,rightMargin,(int) (bottom/100*base));
        }

        public void meMargin(float left, float top, float right, float bottom){
            this.setMargin((int) (left/100*pWidth),(int) (top/100*pHeight),(int) (right/100*pWidth),(int) (bottom/100*pHeight));
        }

        private void setMargin(int left, int top, int right, int bottom ){
            ((ViewGroup.MarginLayoutParams) getLayoutParams()).setMargins(left, top, right, bottom);
            leftMargin = left;
            topMargin = top;
            rightMargin = right;
            bottomMargin = bottom;
        }

        public void meLeftPadding(float left){
            leftPadding = (int) (left/100*(float)pWidth);
            setPadding(leftPadding,topPadding,rightPadding,bottomPadding);
        }

        public void meLeftPadding(float left, float base){
            leftPadding = (int) (left/100*(float)base);
            setPadding(leftPadding,topPadding,rightPadding,bottomPadding);
        }

        public void meTopPadding(float top){
            topPadding = (int) (top/100*(float)pWidth);
            setPadding(leftPadding,topPadding,rightPadding,bottomPadding);
        }

        public void meTopPadding(float top, float base){
            topPadding = (int) (top/100*(float)base);
            setPadding(leftPadding,topPadding,rightPadding,bottomPadding);
        }

        public void meRightPadding(float right){
            rightPadding = (int) (right/100*(float)pWidth);
            setPadding(leftPadding,topPadding,rightPadding,bottomPadding);
        }

        public void meRightPadding(float right, float base){
            rightPadding = (int) (right/100*(float)base);
            setPadding(leftPadding,topPadding,rightPadding,bottomPadding);
        }

        public void meBottomPadding(float bottom){
            bottomPadding = (int) (bottom/100*(float)pWidth);
            setPadding(leftPadding,topPadding,rightPadding,bottomPadding);
        }

        public void meBottomPadding(float bottom, float base){
            bottomPadding = (int) (bottom/100*(float)base);
            setPadding(leftPadding,topPadding,rightPadding,bottomPadding);
        }

        public void mePadding(float left, float top , float right, float bottom){
            leftPadding = (int) (left/100*(float)pWidth);
            rightPadding = (int) (right/100*(float)pWidth);
            topPadding = (int) (top/100*(float)pHeight);
            bottomPadding = (int) (bottom/100 *(float) pHeight);
            setPadding(leftPadding,topPadding,rightPadding,bottomPadding);
        }

}
