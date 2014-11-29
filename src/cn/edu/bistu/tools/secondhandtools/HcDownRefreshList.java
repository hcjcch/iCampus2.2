package cn.edu.bistu.tools.secondhandtools;

import java.text.SimpleDateFormat;
import java.util.Date;



import com.example.icampus2_2.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class HcDownRefreshList extends ListView implements OnScrollListener {
    View header;// ���������ļ�
    int headerHeight;// ���������ļ��ĸ߶�
    int firstVisibleItem;// ��ǰ��һ���ɼ���item��λ��
    int scrollState;// listview ��ǰ����״̬
    boolean isRemark;// ��ǣ���ǰ����listview������µ�
    int startY;// ����ʱ��Yֵ

    int state;// ��ǰ��״̬
    final int NONE = 0;// ����״̬
    final int PULL = 1;// ��ʾ����״̬
    final int RELESE = 2;// ��ʾ�ͷ�״̬
    final int REFLASHING = 3;// ˢ��״̬
    IReflashListener iReflashListener;//ˢ�����ݵĽӿ�

    private int visibleItemCount;//�ܿ��õ���item����
    private int totalItemCount; //�ܵ�item����
    //private ILoadListener iLoadListener;//�������ػص�
    private View footer;        //�ײ������ļ�
    private boolean isLoading;  //�ж��Ƿ����ڼ�����



    public HcDownRefreshList(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        initView(context);
    }

    public HcDownRefreshList(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        initView(context);
    }

    public HcDownRefreshList(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        initView(context);
    }

    /**
     * ��ʼ�����棬��Ӷ��������ļ��͵ײ����ֵ� listview
     *
     * @param context
     */
    private void initView(Context context) {
        initHeader(context);
        initFooter(context);
    }

    /**
     * ��ʼ��ͷ
     * @param context
     */
    private void initHeader(Context context){
        LayoutInflater inflater = LayoutInflater.from(context);
        header = inflater.inflate(R.layout.hc_list_header, null);
        measureView(header);
        headerHeight = header.getMeasuredHeight();
        topPadding(-headerHeight);
        this.addHeaderView(header);
        this.setOnScrollListener(this);
    }

    /**
     * ��ʼ��β��
     * @param context
     */
    private void initFooter(Context context){
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        footer = layoutInflater.inflate(R.layout.hc_list_footer, null);
        footer.findViewById(R.id.load_layout).setVisibility(View.GONE);
        this.addFooterView(footer);
        this.setOnScrollListener(this);
    }

    /**
     * ֪ͨ�����֣�ռ�õĿ��ߣ�
     *
     * @param view
     */
    private void measureView(View view) {
        ViewGroup.LayoutParams p = view.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int width = ViewGroup.getChildMeasureSpec(0, 0, p.width);
        int height;
        int tempHeight = p.height;
        if (tempHeight > 0) {
            height = MeasureSpec.makeMeasureSpec(tempHeight,
                    MeasureSpec.EXACTLY);
        } else {
            height = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        view.measure(width, height);
    }
    /**
     * ����header ���� �ϱ߾ࣻ
     *
     * @param topPadding
     */
    private void topPadding(int topPadding) {
        header.setPadding(header.getPaddingLeft(), topPadding,
                header.getPaddingRight(), header.getPaddingBottom());
        header.invalidate();
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        // TODO Auto-generated method stub
        //����ʱһֱ�ص���ֱ��ֹͣ����ʱ��ֹͣ�ص�������ʱ�ص�һ�Ρ�
        //firstVisibleItem����ǰ�ܿ����ĵ�һ���б���ID����0��ʼ��
        //visibleItemCount����ǰ�ܿ������б��������С���Ҳ�㣩
        //totalItemCount���б����
        //�ж��Ƿ�������һ��
        this.firstVisibleItem = firstVisibleItem;
        this.visibleItemCount = visibleItemCount;
        this.totalItemCount = totalItemCount;
        
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // TODO Auto-generated method stub
        //���ڹ���ʱ�ص����ص�2-3�Σ���ָû����ص�2�Ρ�scrollState = 2����β��ص�
        //�ص�˳������
        //��1�Σ�scrollState = SCROLL_STATE_TOUCH_SCROLL(1) ���ڹ���
        //��2�Σ�scrollState = SCROLL_STATE_FLING(2) ��ָ�����׵Ķ�������ָ�뿪��Ļǰ����������һ�£�
        //��3�Σ�scrollState = SCROLL_STATE_IDLE(0) ֹͣ����
        //����Ļֹͣ����ʱΪ0������Ļ�������û�ʹ�õĴ�������ָ������Ļ��ʱΪ1��
        //�����û��Ĳ�������Ļ�������Ի���ʱΪ2
        //���������һ����ֹͣ����ʱ��ִ�м���
        this.scrollState = scrollState;
        /*if (firstVisibleItem + visibleItemCount == totalItemCount && scrollState == SCROLL_STATE_IDLE) {
            if (!isLoading) {
                isLoading = true;
                footer.findViewById(R.id.load_layout).setVisibility(View.VISIBLE);
                iLoadListener.onLoad();
            }
        }*/
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (firstVisibleItem == 0) {
                    isRemark = true;
                    startY = (int) ev.getY();
                }
                break;

            case MotionEvent.ACTION_MOVE:
                onMove(ev);
                break;
            case MotionEvent.ACTION_UP:
                if (state == RELESE) {
                    state = REFLASHING;
                    // �����������ݣ�
                    reflashViewByState();
                    iReflashListener.onReflash();
                } else if (state == PULL) {
                    state = NONE;
                    isRemark = false;
                    reflashViewByState();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * �ж��ƶ����̲�����
     *
     * @param ev
     */
    private void onMove(MotionEvent ev) {
        if (!isRemark) {
            return;
        }
        int tempY = (int) ev.getY();
        int space = tempY - startY;
        int topPadding = space - headerHeight;
        switch (state) {
            case NONE:
                if (space > 0) {
                    state = PULL;
                    reflashViewByState();
                }
                break;
            case PULL:
                topPadding(topPadding);
                if (space > headerHeight + 30
                        && scrollState == SCROLL_STATE_TOUCH_SCROLL) {
                    state = RELESE;
                    reflashViewByState();
                }
                break;
            case RELESE:
                topPadding(topPadding);
                if (space < headerHeight + 30) {
                    state = PULL;
                    reflashViewByState();
                } else if (space <= 0) {
                    state = NONE;
                    isRemark = false;
                    reflashViewByState();
                }
                break;
        }
    }

    /**
     * ���ݵ�ǰ״̬���ı������ʾ��
     */
    private void reflashViewByState() {
        TextView tip = (TextView) header.findViewById(R.id.tip);
        ImageView arrow = (ImageView) header.findViewById(R.id.arrow);
        ProgressBar progress = (ProgressBar) header.findViewById(R.id.progress);
        RotateAnimation anim = new RotateAnimation(0, 180,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(500);
        anim.setFillAfter(true);
        RotateAnimation anim1 = new RotateAnimation(180, 0,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        anim1.setDuration(500);
        anim1.setFillAfter(true);
        switch (state) {
            case NONE:
                arrow.clearAnimation();
                topPadding(-headerHeight);
                break;

            case PULL:
                arrow.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
                tip.setText("��������ˢ�£�");
                arrow.clearAnimation();
                arrow.setAnimation(anim1);
                break;
            case RELESE:
                arrow.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
                tip.setText("�ɿ�����ˢ�£�");
                arrow.clearAnimation();
                arrow.setAnimation(anim);
                break;
            case REFLASHING:
                topPadding(50);
                arrow.setVisibility(View.GONE);
                progress.setVisibility(View.VISIBLE);
                tip.setText("����ˢ��...");
                arrow.clearAnimation();
                break;
        }
    }

    /**
     * ��ȡ�����ݣ�
     */
    public void reflashComplete() {
        state = NONE;
        isRemark = false;
        reflashViewByState();
        TextView lastupdatetime = (TextView) header
                .findViewById(R.id.lastupdate_time);
        SimpleDateFormat format = new SimpleDateFormat("yyyy��MM��dd�� hh:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String time = format.format(date);
        lastupdatetime.setText(time);
    }

    /**
     * �������������ݵ���
     */
    /*public void loadComplete(){
        isLoading = false;
        footer.findViewById(R.id.load_layout).setVisibility(View.GONE);
    }*/

    public void setInterface(IReflashListener iReflashListener){
        this.iReflashListener = iReflashListener;
    }

    /*public void setFooterInterface(ILoadListener iLoadListener){
        this.iLoadListener = iLoadListener;
    }*/
    /**
     * ˢ�����ݽӿ�
     * @author hcjcch
     */
    public interface IReflashListener{
        public void onReflash();
    }
    public interface onScroListener {
   		void onDataLoadr(String result);
   	}


    /**
     * �����������ݽӿ�
     * @author hcjcch
     */
    /*public interface ILoadListener{
        public void onLoad();
    }*/
}