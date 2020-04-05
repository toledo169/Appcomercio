package com.example.oaxacacomercio.Helper;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.example.oaxacacomercio.ui.tools.ToolsFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.ResourceBundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public abstract class MySwipeHelper extends ItemTouchHelper.SimpleCallback {

    int buttonWidth;
    private RecyclerView recyclerView;
    private List<Mybutton> buttonList;
    private GestureDetector gestureDetector;
    private int swipePosition=1;
    private float swipeThreshold=0.5f;
    private Map<Integer,List<Mybutton>> buttonBuffer;
    private Queue<Integer> removerQueue;

    private GestureDetector.SimpleOnGestureListener gestureListener= new GestureDetector.SimpleOnGestureListener(){
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            for (Mybutton button:buttonList ){
                if (button.onClick(e.getX(),e.getY()))
                    break;

            }
            return true;
        }
    };
    private View.OnTouchListener onTouchListener=new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {

            if(swipePosition<0)return false;
            Point point= new Point((int) motionEvent.getRawX(), (int) motionEvent.getRawY());
            RecyclerView.ViewHolder swipeViewHolder = recyclerView.findViewHolderForAdapterPosition(swipePosition);
            View swipedItem= swipeViewHolder.itemView;
            Rect rect=new Rect();
            swipedItem.getGlobalVisibleRect(rect);
            if(motionEvent.getAction()== MotionEvent.ACTION_DOWN|| motionEvent.getAction()== MotionEvent.ACTION_UP
            ||motionEvent.getAction()==MotionEvent.ACTION_MOVE)
            {
                if (rect.top<point.y && rect.bottom>point.y)
                    gestureDetector.onTouchEvent(motionEvent);
                else {
                    removerQueue.add(swipePosition);
                    swipePosition=-1;
                    recoverySwipedItem();
                }
            }
            return false;
        }
    };



    private synchronized void recoverySwipedItem() {
        while (!removerQueue.isEmpty())
        {
        int pos=removerQueue.poll();
        if (pos>-1)
            recyclerView.getAdapter().notifyItemChanged(pos);

        }
    }


    public MySwipeHelper(Context context, RecyclerView recyclerView, int buttonWidth) {
        super(0, ItemTouchHelper.LEFT);
        this.recyclerView=recyclerView;
        this.buttonList= new ArrayList<>();
        this.gestureDetector=new GestureDetector(context,gestureListener);
        this.recyclerView.setOnTouchListener(onTouchListener);
        this.buttonBuffer=new HashMap<>();
        this.buttonWidth=buttonWidth;
        removerQueue=new LinkedList<Integer>()
        {
            @Override
            public boolean add(Integer integer) {
                if (contains(integer))
                    return false;
                else
                    return super.add(integer);
            }
        };
        attachSwipe();

    }

    private void attachSwipe() {
        ItemTouchHelper itemTouchHelper= new ItemTouchHelper(this);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public class Mybutton {
        private String text;
        private int imageResId, textSize, color, pos;
        private RectF clickregion;
        private MybuttonClickListener listener;
        private Context context;
        private Resources resources;

        public Mybutton(Context context,String text,int textSize,int imageResId, int color, MybuttonClickListener listener) {
            this.text = text;
            this.imageResId = imageResId;
            this.textSize = textSize;
            this.color = color;

            this.listener = listener;
            this.context = context;
            resources=context.getResources();
        }
        public boolean onClick(float x, float y){
            if (clickregion!=null && clickregion.contains(x,y)){
                listener.onClick(pos);
                return true;
            }
            return false;
        }
        public void onDraw(Canvas c, RectF rectF, int pos){
            Paint p= new Paint();
            p.setColor(color);
            c.drawRect(rectF,p);
            p.setColor(Color.WHITE);
            p.setTextSize(textSize);
            Rect r= new Rect();
            float cHeight =rectF.height();
            float cWidth = rectF.width();
            p.setTextAlign(Paint.Align.LEFT);
            p.getTextBounds(text,0,text.length(),r);
            float x=0,y=0;
            if (imageResId==0){
                x=cWidth/2f - r.width()/2f - r.left;
                y=cHeight/2f + r.height()/2f -r.bottom;
                c.drawText(text,rectF.left+x,rectF.top+y,p);
            }else{
                Drawable d= ContextCompat.getDrawable(context,imageResId);
                Bitmap bitmap= drawableToBitMap(d);
                c.drawBitmap(bitmap,(rectF.left+rectF.right)/2,(rectF.top+rectF.bottom)/2,p);
            }
            clickregion= rectF;
            this.pos=pos;
        }
    }

    private Bitmap drawableToBitMap(Drawable d) {
        if (d instanceof BitmapDrawable)
            return ((BitmapDrawable)d).getBitmap();
        Bitmap bitmap= Bitmap.createBitmap(d.getIntrinsicWidth(),d.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(bitmap);
        d.setBounds(0,0,canvas.getWidth(),canvas.getHeight());
        d.draw(canvas);
        return bitmap;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int pos= viewHolder.getAdapterPosition();
        if (swipePosition !=pos)
            removerQueue.add(swipePosition);
        swipePosition=pos;
        if (buttonBuffer.containsKey(swipePosition))
            buttonList=buttonBuffer.get(swipePosition);
        else
            buttonList.clear();
        buttonBuffer.clear();
        swipeThreshold=0.5f*buttonList.size()*buttonWidth;
        recoverySwipedItem();
    }

    public float getSwipeThreshold(RecyclerView.ViewHolder viewHolder) {
        return swipeThreshold;
    }

    @Override
    public float getSwipeEscapeVelocity(float defaultValue) {
        return 0.1f*defaultValue;
    }

    @Override
    public float getSwipeVelocityThreshold(float defaultValue) {
        return 5.0f*defaultValue;
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        int pos= viewHolder.getAdapterPosition();
        float translationX=dX;
        View itemView= viewHolder.itemView;
        if (pos<0){
            swipePosition=pos;
            return;
        }
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE)
        {
        if (dX<0)
        {
            List<Mybutton> buffer= new ArrayList<>();
            if(!buttonBuffer.containsKey(pos))
            {
                instanciateMyButton(viewHolder,buffer);
                buttonBuffer.put(pos,buffer);
            }
            else{
                buffer= buttonBuffer.get(pos);
            }
            translationX=dX*buffer.size()*buttonWidth/itemView.getWidth();
            drawButton(c,itemView,buffer,pos,translationX);
        }
        }
        super.onChildDraw(c,recyclerView,viewHolder,translationX,dY,actionState,isCurrentlyActive);
    }

    private void drawButton(Canvas c, View itemView, List<Mybutton> buffer, int pos, float translationX) {
    float right= itemView.getRight();
    float dButtonWidth= -1*translationX/buffer.size();
    for (Mybutton button:buffer)
    {
    float left= right - dButtonWidth;
    button.onDraw(c,(new RectF(left,itemView.getTop(),right,itemView.getBottom())),pos);
    right=left;
    }
    }

    public abstract void instanciateMyButton(RecyclerView.ViewHolder viewHolder, List<Mybutton> buffer);

}
