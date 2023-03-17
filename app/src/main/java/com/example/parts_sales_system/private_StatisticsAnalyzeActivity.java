package com.example.parts_sales_system;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.parts_sales_system.data.api_connection.getData;
import com.example.parts_sales_system.ui.statistics_analyze.CardListViewAdapter;
import com.example.parts_sales_system.ui.statistics_analyze.DateAdapter;
import com.example.parts_sales_system.ui.statistics_analyze.SpecialCalendar;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class private_StatisticsAnalyzeActivity extends AppCompatActivity implements OnGestureListener {
    private static String TAG = "MainActivity";
    private ViewFlipper flipper1 = null;
    private GridView gridView = null;
    private GestureDetector gestureDetector = null;
    private int year_c = 0;
    private int month_c = 0;
    private int day_c = 0;
    private int week_c = 0;
    private int week_num = 0;
    private String currentDate = "";
    private DateAdapter dateAdapter;
    private int daysOfMonth = 0;
    private int dayOfWeek = 0;
    private int weeksOfMonth = 0;
    private SpecialCalendar sc = null;
    private boolean isLeapyear = false;
    private int selectPostion = 0;
    private String dayNumbers[] = new String[7];
    private TextView tvDate;
    private int currentYear;
    private int currentMonth;
    private int currentWeek;
    private int currentDay;
    private int currentNum;
    ImageButton jump;
    private ProgressDialog progressDialog;
    public int getWeeksOfMonth(int year, int month) {
        int preMonthRelax = 0;
        int dayFirst = getWhichDayOfWeek(year, month);
        int days = sc.getDaysOfMonth(sc.isLeapYear(year), month);
        if (dayFirst != 7) {
            preMonthRelax = dayFirst;
        }
        if ((days + preMonthRelax) % 7 == 0) {
            weeksOfMonth = (days + preMonthRelax) / 7;
        } else {
            weeksOfMonth = (days + preMonthRelax) / 7 + 1;
        }
        return weeksOfMonth;
    }
    public int getWhichDayOfWeek(int year, int month) {
        return sc.getWeekdayOfMonth(year, month);

    }
    public int getLastDayOfWeek(int year, int month) {
        return sc.getWeekDayOfLastMonth(year, month,
                sc.getDaysOfMonth(isLeapyear, month));
    }

    public void getCalendar(int year, int month) {
        isLeapyear = sc.isLeapYear(year);
        daysOfMonth = sc.getDaysOfMonth(isLeapyear, month);
        dayOfWeek = sc.getWeekdayOfMonth(year, month);
    }

    public int getWeeksOfMonth() {
        int preMonthRelax = 0;
        if (dayOfWeek != 7) {
            preMonthRelax = dayOfWeek;
        }
        if ((daysOfMonth + preMonthRelax) % 7 == 0) {
            weeksOfMonth = (daysOfMonth + preMonthRelax) / 7;
        } else {
            weeksOfMonth = (daysOfMonth + preMonthRelax) / 7 + 1;
        }
        return weeksOfMonth;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_statistics_analyze);
        showProgressDialog("请稍后","正在计算");
        tvDate = (TextView) findViewById(R.id.tv_date);
        tvDate.setText(year_c + "年" + month_c + "月" + day_c + "日");
        jump=findViewById(R.id.jump);
        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickDialog(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        Intent intent=new Intent(private_StatisticsAnalyzeActivity.this,private_StatisticsAnalyzeActivity.class);
                        intent.putExtra("flag",1);
                        intent.putExtra("date",year + "-" + (month + 1) + "-" + day);
                        startActivity(intent);
                    }
                }, tvDate.getText().toString());
            }
        });
        initCalendar();
        initCard();
//        hideProgressDialog();
    }
    List number;
    List money;
    List type;
    String date;
    List data;
    //跳转刷新数据
    public void initCard(){
        ListView listview=(ListView) this.findViewById(R.id.listView);
        Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1: {
                        Bundle bundle=msg.getData();
                        number=(List) bundle.getSerializable("number");
                        money=(List)bundle.getSerializable("money");
                        type=(List) bundle.getSerializable("type");
                        data.add(number.get(1));
                        data.add(number.get(2));
                        data.add(Integer.parseInt(number.get(3).toString())-Integer.parseInt(number.get(0).toString()));
                    }
                }
                initCircle();
                String[] Title={"入库数据统计","出库数据统计","库存数据盘点","总订单数据"};
                int[] Images={R.drawable.in_invent,R.drawable.out_invent,R.drawable.inventory,R.drawable.sum};
//                String[] Content={"(1)这里放一些内容，这里放一些图片，这里扩展性很强",
//                        "(2)这里放一些内容，这里放一些图片，这里扩展性很强",
//                        "(3)这里放一些内容，这里放一些图片，这里扩展性很强",
//                        "(4)这里放一些内容，这里放一些图片，这里扩展性很强"};

                //将数据封装成数据源
                List<Map<String,Object>> list=new ArrayList<Map<String, Object>>();
                for(int i=0;i<Title.length;i++){
                    Map<String,Object> map=new HashMap<String, Object>();
                    map.put("title",Title[i]);
                    map.put("img",Images[i]);
//                    map.put("content",Content[i]);
                    map.put("number",number.get(i));
                    map.put("money",money.get(i));
                    map.put("type",type.get(i));
                    list.add(map);
                }
                listview.setAdapter(new CardListViewAdapter(list,private_StatisticsAnalyzeActivity.this));
                listview.setOnItemClickListener(new ItemClickListener());
                hideProgressDialog();
            }
            class ItemClickListener implements AdapterView.OnItemClickListener {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                number = new ArrayList<>();
                money=new ArrayList<>();
                type=new ArrayList<>();
                data=new ArrayList<>();
                int flag=getIntent().getIntExtra("flag",0);
                if (flag==0){
                    Date d = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
                    date = sdf.format(d);
                }else{date=getIntent().getExtras().getString("date");}
                count("MFJYanDet","MFJYanDetNum","MFJChuDetNum","MFJChuDet","MFJOrderDet","MFJOrderDetShu",date,number);
                count("MFJOrder","MFJOrderAllPrice","MFJChuKuanNum","MFJChuKuan","MFJOrder","MFJOrderAllPrice",date,money);
                count_type("MFJYanDet","MFJChuDet",date,type);
                Message msg=new Message();
                Bundle bundle=new Bundle();
                bundle.putSerializable("number", (Serializable) number);
                bundle.putSerializable("money",(Serializable) money);
                bundle.putSerializable("type",(Serializable) type);
                msg.setData(bundle);
                //msg.obj=number;
                msg.what=1;
                handler.sendMessage(msg);
            }
        }).start();
    }
    public void count(String dbname1,String column1,String column2,String dbname2,String dbname3,String column3,String date,List list){
        try {
            JSONArray jsonArray_in=getData.getData(dbname1,"{searchdatemin:'"+date+"',searchdatemax:'"+date+"'}");
            if (jsonArray_in.length()!=0){
                int count_in=0;
                for (int i=0;i<jsonArray_in.length();i++) {
                    JSONObject jsonObject_in=new JSONObject(jsonArray_in.getString(i));
                    count_in+=Integer.parseInt(jsonObject_in.getString(column1));
                }
                list.add(count_in);
            }else{list.add(0);}
            JSONArray jsonArray_out=getData.getData(dbname2,"{searchdatemin:'"+date+"',searchdatemax:'"+date+"'}");
//        .split("-")[0]+"-"+date.split("-")[1]+"-"+(Integer.parseInt(date.split("-")[2])+1)
            if(jsonArray_out.length()!=0){
                int count_out=0;
                for(int i=0;i<jsonArray_out.length();i++){
                    JSONObject jsonObject_out=new JSONObject(jsonArray_out.getString(i));
                    count_out+=Integer.parseInt(jsonObject_out.getString(column2));
                }
                list.add(count_out);
            }else{list.add(0);}
            int count_in_current=0;
            JSONArray jsonArray_in_current=getData.getData(dbname1,"{searchdatemin:'',searchdatemax:'"+date+"'}");
            if(jsonArray_in_current.length()!=0){
                for(int i=0;i<jsonArray_in_current.length();i++){
                    JSONObject jsonObject_in_current=new JSONObject(jsonArray_in_current.getString(i));
                    count_in_current+=Integer.parseInt(jsonObject_in_current.getString(column1));
                }
            }
            int count_out_current=0;
            JSONArray jsonArray_out_current=getData.getData(dbname2,"{searchdatemin:'',searchdatemax:'"+date+"'}");
            if(jsonArray_out_current.length()!=0){
                for(int i=0;i<jsonArray_out_current.length();i++){
                    JSONObject jsonObject_out_current=new JSONObject(jsonArray_out_current.getString(i));
                    count_out_current+=Integer.parseInt(jsonObject_out_current.getString(column2));
                }
            }
            list.add(count_in_current-count_out_current);
            int count_insum=0;
            JSONArray jsonArray_insum=getData.getData(dbname3,"{searchdatemin:'',searchdatemax:''}");
            if(jsonArray_insum.length()!=0){
                for(int i=0;i<jsonArray_insum.length();i++){
                    JSONObject jsonObject_insum=new JSONObject(jsonArray_insum.getString(i));
                    count_insum+=Integer.parseInt(jsonObject_insum.getString(column3));
                }
            }
            list.add(count_insum);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void count_type(String dbname1,String dbname2,String date,List list){
        try {
            JSONArray jsonArray_in=getData.getData(dbname1,"{searchdatemin:'"+date+"',searchdatemax:'"+date+"'}");
            int count_in=jsonArray_in.length();
            list.add(count_in);
            JSONArray jsonArray_out=getData.getData(dbname2,"{searchdatemin:'"+date+"',searchdatemax:'"+date.split("-")[0]+"-"+date.split("-")[1]+"-"+(Integer.parseInt(date.split("-")[2])+1)+"'}");
            int count_out=jsonArray_out.length();
            list.add(count_out);
            JSONArray jsonArray_in_current=getData.getData(dbname1,"{searchdatemin:'',searchdatemax:'"+date+"'}");
            int count_in_current=jsonArray_in_current.length();
            JSONArray jsonArray_out_current=getData.getData(dbname2,"{searchdatemin:'',searchdatemax:'"+date+"'}");
            int count_out_current=jsonArray_out_current.length();
            list.add(count_in_current-count_out_current);
            JSONArray jsonArray_insum=getData.getData("MFJOrder","{searchdatemin:'',searchdatemax:''}");
            int count_insum=jsonArray_insum.length();
            list.add(count_insum);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void initCircle(){
        PieChart pieChart = findViewById(R.id.picChart);
        pieChart.setNoDataText("No chart data available.");
        ArrayList<String> name = new ArrayList<>(Arrays.asList("已出库密封件数","库存数量","已订货未入库数量"));
        List<PieEntry> pieEntries = new ArrayList<>();
        ArrayList<String> rate = new ArrayList<>();
        //计算百分比
        for(int i = 0; i < data.size(); i++) {
            DecimalFormat df = new DecimalFormat("0.00%");
            String decimal = df.format(Integer.parseInt(data.get(i).toString())/(Integer.parseInt(data.get(0).toString())+Integer.parseInt(data.get(1).toString())+Integer.parseInt(data.get(2).toString())));
            PieEntry pieEntry = new PieEntry(Integer.parseInt(data.get(i).toString())/(Integer.parseInt(data.get(0).toString())+Integer.parseInt(data.get(1).toString())+Integer.parseInt(data.get(2).toString())), name.get(i));
            pieEntries.add(pieEntry);
            rate.add(decimal);
            //name[i] = name[i] + decimal;
        }
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#6BE61A"));
        colors.add(Color.parseColor("#4474BB"));
        colors.add(Color.parseColor("#AA7755"));

        PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
        pieDataSet.setSliceSpace(1f); //设置个饼状图之间的距离
        pieDataSet.setColors(colors);
        pieDataSet.setValueTextSize(0);
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.setDrawCenterText(true);//显示中间文字
        pieChart.setCenterText("密封件数量统计");
        pieChart.setCenterTextSize(16);//中间文字大小
        pieChart.setCenterTextColor(Color.parseColor("#3CC4C4"));//中间文字颜色
        pieChart.setEntryLabelTextSize(0);//标签不显示
        Description description = new Description();//设置描述
        description.setText("");
        pieChart.setDescription(description);
        Legend legend = pieChart.getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setTextSize(10);
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                if(e == null) {
                    return;
                }
                if(pieEntries.get(0).getValue() == e.getY()) {
                    Toast.makeText(private_StatisticsAnalyzeActivity.this,name.get(0) + "占比" + rate.get(0),Toast.LENGTH_SHORT).show();
//                    ToastUtil.showMessage(private_StatisticsAnalyzeActivity.this, name.get(0) + "占比" + rate.get(0));
                }else if(pieEntries.get(1).getValue() == e.getY()) {
                    Toast.makeText(private_StatisticsAnalyzeActivity.this,name.get(1) + "占比" + rate.get(1),Toast.LENGTH_SHORT).show();
//                    ToastUtil.showMessage(private_StatisticsAnalyzeActivity.this, name.get(1) + "占比" + rate.get(1));
                }else if(pieEntries.get(2).getValue() == e.getY()) {
                    Toast.makeText(private_StatisticsAnalyzeActivity.this,name.get(2) + "占比" + rate.get(2),Toast.LENGTH_SHORT).show();
//                    ToastUtil.showMessage(private_StatisticsAnalyzeActivity.this, name.get(2) + "占比" + rate.get(2));
                }
            }
            @Override
            public void onNothingSelected() {
            }
        });
        pieChart.animateXY(1000, 1000);
        pieChart.invalidate();
    }

    //跳转刷新日历
    public void initCalendar(){
        int flag=getIntent().getIntExtra("flag",0);
        if (flag==0){
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
            currentDate = sdf.format(date);
            year_c = Integer.parseInt(currentDate.split("-")[0]);
            month_c = Integer.parseInt(currentDate.split("-")[1]);
            day_c = Integer.parseInt(currentDate.split("-")[2]);
            currentYear = year_c;
            currentMonth = month_c;
            currentDay = day_c;
            sc = new SpecialCalendar();
            getCalendar(year_c, month_c);
            week_num = getWeeksOfMonth();
            currentNum = week_num;
            if (dayOfWeek == 7) {
                week_c = currentDay / 7 + 1;
            } else {
                if (currentDay <= (7 - dayOfWeek)) {
                    week_c = 1;
                } else {
                    if ((currentDay - (7 - dayOfWeek)) % 7 == 0) {
                        week_c = (currentDay - (7 - dayOfWeek)) / 7 + 1;
                    } else {
                        week_c = (currentDay - (7 - dayOfWeek)) / 7 + 2;
                    }
                }
            }
            currentWeek = week_c;
            getCurrent();
            gestureDetector = new GestureDetector(this);
            flipper1 = (ViewFlipper) findViewById(R.id.flipper1);
            dateAdapter = new DateAdapter(this, currentYear, currentMonth,currentWeek, currentWeek == 1 ? true : false);
            addGridView();
            dayNumbers = dateAdapter.getDayNumbers();
            gridView.setAdapter(dateAdapter);
            selectPostion = dateAdapter.getTodayPosition();
            gridView.setSelection(selectPostion);
            flipper1.addView(gridView, 0);
            tvDate.setText(currentYear + "-" + (currentMonth) + "-" + currentDay);
        }
        else{
            currentDate=getIntent().getExtras().getString("date");
            System.out.println(currentDate);
            year_c = Integer.parseInt(currentDate.split("-")[0]);
            month_c = Integer.parseInt(currentDate.split("-")[1]);
            day_c = Integer.parseInt(currentDate.split("-")[2]);
            currentYear = year_c;
            currentMonth = month_c;
            currentDay = day_c;
            sc = new SpecialCalendar();
            getCalendar(year_c, month_c);
            week_num = getWeeksOfMonth();
            currentNum = week_num;
            if (dayOfWeek == 7) {
                week_c = currentDay / 7 + 1;
            } else {
                if (currentDay <= (7 - dayOfWeek)) {
                    week_c = 1;
                } else {
                    if ((currentDay - (7 - dayOfWeek)) % 7 == 0) {
                        week_c = (currentDay - (7 - dayOfWeek)) / 7 + 1;
                    } else {
                        week_c = (currentDay - (7 - dayOfWeek)) / 7 + 2;
                    }
                }
            }
            currentWeek = week_c;
            getCurrent();
            gestureDetector = new GestureDetector(this);
            flipper1 = (ViewFlipper) findViewById(R.id.flipper1);
            dateAdapter = new DateAdapter(this, currentYear, currentMonth,currentWeek, currentWeek == 1 ? true : false);
            addGridView();
            dayNumbers = dateAdapter.getDayNumbers();
            gridView.setAdapter(dateAdapter);
            selectPostion = dateAdapter.getDayPosition(String.valueOf(currentYear),String.valueOf(currentMonth),String.valueOf(currentDay));
            gridView.setSelection(selectPostion);
            flipper1.addView(gridView, 0);
            tvDate.setText(currentYear + "-" + (currentMonth) + "-" + currentDay);
        }
    }
    //点击快速跳转出现的日期选择框
    public void showDatePickDialog(DatePickerDialog.OnDateSetListener listener, String curDate) {
        Calendar calendar = Calendar.getInstance();
        int year = 0,month = 0,day = 0;
        try {
            year =Integer.parseInt(curDate.substring(0,curDate.indexOf("-"))) ;
            month =Integer.parseInt(curDate.substring(curDate.indexOf("-")+1,curDate.lastIndexOf("-")))-1 ;
            day =Integer.parseInt(curDate.substring(curDate.lastIndexOf("-")+1,curDate.length())) ;
        } catch (Exception e) {
            e.printStackTrace();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day=calendar.get(Calendar.DAY_OF_MONTH);
        }
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,DatePickerDialog.THEME_HOLO_LIGHT,listener, year,month , day);
        datePickerDialog.show();
    }
    private void addGridView() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        gridView = new GridView(this);
        gridView.setNumColumns(7);
        gridView.setGravity(Gravity.CENTER_VERTICAL);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gridView.setVerticalSpacing(1);
        gridView.setHorizontalSpacing(1);
        gridView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return private_StatisticsAnalyzeActivity.this.gestureDetector.onTouchEvent(event);
            }
        });

        //刷新数据的地方
        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //Log.i(TAG, "day:" + dayNumbers[position]);
                selectPostion = position;
                dateAdapter.setSeclection(position);
                dateAdapter.notifyDataSetChanged();
                tvDate.setText(dateAdapter.getCurrentYear(selectPostion) + "年"+ dateAdapter.getCurrentMonth(selectPostion) + "月"+ dayNumbers[position] + "日");
                Intent intent=new Intent(private_StatisticsAnalyzeActivity.this,private_StatisticsAnalyzeActivity.class);
                intent.putExtra("flag",1);
                intent.putExtra("date",dateAdapter.getCurrentYear(selectPostion) + "-" + dateAdapter.getCurrentMonth(selectPostion) + "-" + dayNumbers[position]);
                startActivity(intent);
            }
        });
        gridView.setLayoutParams(params);
    }
    @Override
    protected void onPause() {
        super.onPause();
    }
    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }
    @Override
    public void onShowPress(MotionEvent e) {}
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,float distanceY) {
        return false;
    }
    @Override
    public void onLongPress(MotionEvent e) {}
    public void getCurrent() {
        if (currentWeek > currentNum) {
            if (currentMonth + 1 <= 12) {
                currentMonth++;
            } else {
                currentMonth = 1;
                currentYear++;
            }
            currentWeek = 1;
            currentNum = getWeeksOfMonth(currentYear, currentMonth);
        } else if (currentWeek == currentNum) {
            if (getLastDayOfWeek(currentYear, currentMonth) == 6) {
            } else {
                if (currentMonth + 1 <= 12) {
                    currentMonth++;
                } else {
                    currentMonth = 1;
                    currentYear++;
                }
                currentWeek = 1;
                currentNum = getWeeksOfMonth(currentYear, currentMonth);
            }

        } else if (currentWeek < 1) {
            if (currentMonth - 1 >= 1) {
                currentMonth--;
            } else {
                currentMonth = 12;
                currentYear--;
            }
            currentNum = getWeeksOfMonth(currentYear, currentMonth);
            currentWeek = currentNum - 1;
        }
    }
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,float velocityY) {
        int gvFlag = 0;
        if (e1.getX() - e2.getX() > 80) {
            addGridView();
            currentWeek++;
            getCurrent();
            dateAdapter = new DateAdapter(this, currentYear, currentMonth,currentWeek, currentWeek == 1 ? true : false);
            dayNumbers = dateAdapter.getDayNumbers();
            gridView.setAdapter(dateAdapter);
            tvDate.setText(dateAdapter.getCurrentYear(selectPostion) + "年"+ dateAdapter.getCurrentMonth(selectPostion) + "月"+ dayNumbers[selectPostion] + "日");
            Intent intent=new Intent(private_StatisticsAnalyzeActivity.this,private_StatisticsAnalyzeActivity.class);
            intent.putExtra("flag",1);
            intent.putExtra("date",dateAdapter.getCurrentYear(selectPostion) + "-" + dateAdapter.getCurrentMonth(selectPostion) + "-" + dayNumbers[selectPostion]);
            startActivity(intent);
            gvFlag++;
            flipper1.addView(gridView, gvFlag);
            dateAdapter.setSeclection(selectPostion);
            this.flipper1.setInAnimation(AnimationUtils.loadAnimation(this,R.anim.push_left_in));
            this.flipper1.setOutAnimation(AnimationUtils.loadAnimation(this,R.anim.push_left_out));
            this.flipper1.showNext();
            flipper1.removeViewAt(0);
            return true;
        } else if (e1.getX() - e2.getX() < -80) {
            addGridView();
            currentWeek--;
            getCurrent();
            dateAdapter = new DateAdapter(this, currentYear, currentMonth,currentWeek, currentWeek == 1 ? true : false);
            dayNumbers = dateAdapter.getDayNumbers();
            gridView.setAdapter(dateAdapter);
            tvDate.setText(dateAdapter.getCurrentYear(selectPostion) + "年"+ dateAdapter.getCurrentMonth(selectPostion) + "月"+ dayNumbers[selectPostion] + "日");
            Intent intent=new Intent(private_StatisticsAnalyzeActivity.this,private_StatisticsAnalyzeActivity.class);
            intent.putExtra("flag",1);
            intent.putExtra("date",dateAdapter.getCurrentYear(selectPostion) + "-" + dateAdapter.getCurrentMonth(selectPostion) + "-" + dayNumbers[selectPostion]);
            startActivity(intent);
            gvFlag++;
            flipper1.addView(gridView, gvFlag);
            dateAdapter.setSeclection(selectPostion);
            this.flipper1.setInAnimation(AnimationUtils.loadAnimation(this,R.anim.push_right_in));
            this.flipper1.setOutAnimation(AnimationUtils.loadAnimation(this,R.anim.push_right_out));
            this.flipper1.showPrevious();
            flipper1.removeViewAt(0);
            return true;
        }
        return false;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Toast.makeText(this, "当前页面禁止侧滑返回", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }
    //选项菜单跳转主界面
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.MainActivity:
                Intent intent=new Intent(private_StatisticsAnalyzeActivity.this,public_MainActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void showProgressDialog(String title, String message) {
        if (progressDialog == null) {
            progressDialog = ProgressDialog.show(private_StatisticsAnalyzeActivity.this, title,
                    message, true, false);
        } else if (progressDialog.isShowing()) {
            progressDialog.setTitle(title);
            progressDialog.setMessage(message);
        }
        progressDialog.show();
    }
    //     隐藏提示加载
    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}