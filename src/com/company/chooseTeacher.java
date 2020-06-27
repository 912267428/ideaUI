package com.company;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

public class chooseTeacher {
    JDialog jd ;
    public chooseTeacher(JFrame frame,String id,String name,String chooseId,String chooseName) {
        // TODO 自动生成的构造函数存根
        jd = new JDialog(frame,chooseName+"的课程",true);
        jd.setSize(800, 600);
        jd.setLocationRelativeTo(null);
        Container c = jd.getContentPane();
        c.setLayout(null);

        Db db = new Db();
        int courseNum = 0;
        String sql = "select count(*) from course where bywho='"+chooseId+"' and pass=1";
        try {
            ResultSet rs =  db.inquire(sql);
            if(rs.next())
                courseNum = rs.getInt(1);
            db.close();
        } catch (Exception e1) {
            // TODO 自动生成的 catch 块
            e1.printStackTrace();
        }
        String columnNames[] = {"课程名称","课程课时","课程要求","课程内容","考核方式"};
        String rowData[][] = new String[courseNum][];
        String courseName[] = new String[courseNum];
        sql = "select * from course where bywho='"+chooseId+"' and pass=1";
        try {
            ResultSet rs =  db.inquire(sql);
            for(int i=0;i<courseNum;i++)
            {
                rs.next();
                rowData[i] = new String[5];
                courseName[i] = rowData[i][0]  = rs.getString("name");
                rowData[i][1]  = rs.getString("time");
                rowData[i][2]  = rs.getString("ask");
                rowData[i][3]  = rs.getString("detail");
                rowData[i][4]  = rs.getString("examine");
            }
            db.close();
        } catch (Exception e1) {
            // TODO 自动生成的 catch 块
            e1.printStackTrace();
        }
        JButton choose = new JButton("确定选课");
        choose.setBounds(500, 320, 100, 40);

        JList<String> jlChoose = new JList<>(courseName);
        jlChoose.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); //可以选中任意课程
        JScrollPane jsChoose = new JScrollPane(jlChoose);
        jsChoose.setBounds(400, 320, 100, 130);

        JTable teaScourse = new JTable(rowData,columnNames);
        JScrollPane tablePane = new JScrollPane(teaScourse);
        tablePane.setBounds(100, 100, 600, 200);

        choose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<String> courseList = jlChoose.getSelectedValuesList();
                if(courseList.size() == 0)
                {
                    JDialog admlog = new JDialog(jd,"error",true);
                    admlog.setSize(300,80);
                    admlog.setLocationRelativeTo(null);
                    JLabel admjl = new JLabel("请选择课程");
                    Container admc = admlog.getContentPane();
                    admc.setLayout(new FlowLayout());
                    JButton admbtn = new JButton("确定");
                    admbtn.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            admlog.setVisible(false);
                        }
                    });
                    admc.add(admjl);
                    admc.add(admbtn);
                    admlog.setVisible(true);
                }
                else {
                    int cfindex = 0;
                    int index=1;
                    String stuIf[] = new String[8];
                    String sql = "select * from student where id='"+id+"'";
                    try {
                        ResultSet rs = db.inquire(sql);

                        if(rs.next())
                        {
                            stuIf[0] = rs.getString("name");
                            stuIf[1] = rs.getString("age");
                            stuIf[2] = rs.getString("sex");
                            stuIf[3] = rs.getString("profession");
                            stuIf[4] = rs.getString("class");
                            stuIf[5] = rs.getString("stuid");
                            stuIf[6] = rs.getString("email");
                            stuIf[7] = rs.getString("phonenumber");
                        }

                        db.close();
                    } catch (Exception e1) {
                        // TODO 自动生成的 catch 块
                        e1.printStackTrace();
                    }

                    for(int i=0;i<courseList.size();i++)
                    {
                        sql = "select name from "+courseList.get(i)+"_"+chooseId;
                        ResultSet rs;
                        try {
                            rs = db.inquire(sql);
                            while(rs.next())
                            {
                                if(rs.getString("name").equals(name))
                                    cfindex = 1;
                            }
                            db.close();
                        } catch (Exception e1) {
                            // TODO 自动生成的 catch 块
                            e1.printStackTrace();
                        }

                    }
                    if(cfindex == 1)
                    {
                        JDialog admlog = new JDialog(jd,"error",true);
                        admlog.setSize(300,80);
                        admlog.setLocationRelativeTo(null);
                        JLabel admjl = new JLabel("有重复选课，请重选");
                        Container admc = admlog.getContentPane();
                        admc.setLayout(new FlowLayout());
                        JButton admbtn = new JButton("确定");
                        admbtn.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                admlog.setVisible(false);
                            }
                        });
                        admc.add(admjl);
                        admc.add(admbtn);
                        admlog.setVisible(true);
                    }
                    else
                    {
                        for(int i=0;i<courseList.size();i++)
                        {
                            sql = "INSERT INTO "+courseList.get(i)+"_"+chooseId+" (`name`, `age`, `sex`, `PROFESSION`, `class`, `STUID`, `email`, `phonenumber`) VALUES ('";
                            sql+=stuIf[0]+"',"+stuIf[1]+",'"+stuIf[2]+"','"+stuIf[3]+"','"+stuIf[4]+"','"+stuIf[5]+"','"+stuIf[6]+"','"+stuIf[7]+"')";
                            try {
                                if(!db.insertORupdateORdelete(sql))
                                    index = 0;
                            } catch (Exception e1) {
                                // TODO 自动生成的 catch 块
                                e1.printStackTrace();
                            }
                        }
                        if(index == 1)
                        {
                            JDialog admlog = new JDialog(jd,"",true);
                            admlog.setSize(300,80);
                            admlog.setLocationRelativeTo(null);
                            JLabel admjl = new JLabel("选课成功");
                            Container admc = admlog.getContentPane();
                            admc.setLayout(new FlowLayout());
                            JButton admbtn = new JButton("确定");
                            admbtn.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {

                                    admlog.setVisible(false);
                                    jd.setVisible(false);
                                }
                            });
                            admc.add(admjl);
                            admc.add(admbtn);
                            admlog.setVisible(true);
                        }
                    }
                }
            }

        });

        c.add(choose);c.add(jsChoose);c.add(tablePane);

    }
}