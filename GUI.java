/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI_PACKAGE;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 *
 * @author SMAIL
 */
public final class GUI extends JFrame implements ActionListener{
    private static final long serialVersionUID = 1L;
    /*************************************************
     * author Smail Gourmi 
     * source Andy Evans
     * Date 07/05/2018
     * Title - Suduko puzzle
     * @param args 
     *************************************************/
    private int count0;
    private int count1; 
    JButton jbtNewPuzzle = new JButton("New Puzzle");
    JButton jbtHints     = new JButton("Hints");
    JButton jbtSolve     = new JButton("Solve");
    JButton jbtClear     = new JButton("Clear");
    JButton jbtExit     = new JButton("Exit");
    Font myFont;
    final int row = 9;
    final int colum = 9;
    final Color[] col ;
    //Initialize the array
    final JTextField[][] subPanels = new JTextField[row][colum];
    final JTextField result;
    int[][] suduko = new int[GUI.this.row][GUI.this.colum];
    @SuppressWarnings("empty-statement")
    public GUI(){
        super("Suduko Solver @ SMAIL GOURMI");
        this.count0 = 0;
        this.myFont = new Font("Serif", Font.BOLD, 48);
        this.col = new Color[4];
        col[0] = Color.blue;
        col[1] = Color.yellow;
        col[2] = Color.green;
        col[3] = Color.red;
        Color b;
        /*********************************
         *Layout Grid to hold all your component 
         *********************************/
        final JPanel bigPanel = new JPanel(new GridBagLayout());
        GridBagConstraints components = new GridBagConstraints();
        
        
        /*********************************************
         * Panel TO HOLD
         * 9x9 PUZZLE GRID
         *********************************************/
        final JPanel GuiPanel = new JPanel(new GridLayout(row,colum));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        bigPanel.add(GuiPanel);
        setLocationRelativeTo(null);
        setVisible(true);
        Border outerBorder = BorderFactory.createLineBorder(Color.black,2);
        Border innerBorder = BorderFactory.createLineBorder(Color.black,1);
        GuiPanel.setBorder(outerBorder);
        for(int r=0;r<this.row;r++){
            for(int c=0;c<this.colum;c++){
                subPanels[r][c] = new JTextField("");
                subPanels[r][c].setVisible(true);
                subPanels[r][c].setColumns(1);
                subPanels[r][c].setBorder(innerBorder);
                subPanels[r][c].setBackground(col[randInt(0, col.length-1)]);
                subPanels[r][c].setFont(myFont);
                b = subPanels[r][c].getBackground();
                subPanels[r][c].setForeground(setFontColor(b));
                subPanels[r][c].setHorizontalAlignment(JTextField.CENTER);
                subPanels[r][c].setSize(50,50);
                subPanels[r][c].setPreferredSize(new Dimension(50, 50));
                GuiPanel.add(subPanels[r][c]);
            }
        }
        components.gridx=0;
        components.gridy=0;
        bigPanel.add(GuiPanel,components);
        /*********************************************************
         * Panel To Hold Buttons
         *********************************************************/
        final JPanel ButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        ButtonPanel.setLayout(new BorderLayout());
        ButtonPanel.add(jbtNewPuzzle);
        ButtonPanel.add(jbtHints);
        ButtonPanel.add(jbtSolve);
        ButtonPanel.add(jbtClear);
        ButtonPanel.add(jbtExit);
        Border LineBorder = new LineBorder(Color.lightGray);
        ButtonPanel.setBorder(LineBorder);
        BoxLayout horizontal = new BoxLayout(ButtonPanel,BoxLayout.X_AXIS);
        ButtonPanel.setLayout(horizontal);
        FlowLayout flow = new FlowLayout();
        ButtonPanel.setLayout(flow);
        ButtonPanel.setVisible(true);
        components.gridy=1;
        bigPanel.add(ButtonPanel,components);
        /***********************************
         * set field for time it takes to solve 
         ***********************************/
        this.result = new JTextField();
        this.result.setBackground(col[randInt(0, col.length-1)]);
        Font fontResult = new Font("Serif", Font.BOLD, 18);
        this.result.setFont(fontResult);
        b = this.result.getBackground();
        this.result.setForeground(setFontColor(b));
        this.result.setHorizontalAlignment( JTextField.CENTER);
        components.gridy=2;
        components.fill=GridBagConstraints.HORIZONTAL;
        bigPanel.add(result,components);
        /**********************************
         * setting Actions For Each Button
         **********************************/
        jbtSolve.addActionListener((ActionListener) this); 
        jbtClear.addActionListener((ActionListener) this); 
        jbtExit.addActionListener((ActionListener)this);
       
        //********************************/
        add(bigPanel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    @Override
    @SuppressWarnings("empty-statement")
             public void actionPerformed(ActionEvent evt) {
                if(evt.getSource()==jbtClear){
                    this.result.setText("");
                    for(int r=0;r<row;r++){
                        for(int c=0;c<colum;c++){
                           subPanels[r][c].setText(null);
                           this.suduko[r][c]=0;
                        }
                    }}
                else if(evt.getSource()==jbtExit){
                        // Runtime.getRuntime().halt(0);
                        System.exit(0);
                        }
                else if(evt.getSource()==jbtSolve){
                    long startTime = System.currentTimeMillis();
                        this.count0=0;
                        this.count1=0;
                        int temp;
                        boolean checked;
                        for(int k=0;k<this.row;k++){
                            for(int p=0;p<this.colum;p++){
                                this.suduko[k][p]=0;
                            }
                        }
                        for (int r = 0; r<this.row;r++) {
                            for (int c = 0; c<this.colum;c++) {
                                checked=true;
                                while (checked) {
                                    temp=randInt(1,9);
                                    int pass = 0;
                                    while( pass<GUI.this.colum ) {
                                        if(suduko[r][pass]==temp || suduko[pass][c]==temp){
                                            checked=true;
                                            this.count0++;
                                            if(this.count0==50){
                                                this.count1++;
                                                for(int zero=0;zero<c;zero++){
                                                    suduko[r][zero]=0;
                                                }
                                                c=0;
                                                this.count0=0;
                                            }
                                            if(this.count1==10){
                                                for(int zero0=r;zero0>=0;zero0--){
                                                    for(int zero1=this.colum-1;zero1>=0;zero1-- ){
                                                        suduko[zero0][zero1]=0;
                                                    }
                                                    this.count1=0;
                                                    r=0;
                                                    c=0;
                                                }
                                            }
                                            break;
                                        }
                                        else{
                                            checked=false;
                                            pass++;
                                        }
                                    }
                                   if(!checked){
                                                    if(r<3 && c<3){
                                                        OUTER_LOOP:
                                                                    for(int start=0;start<3;start++){
                                                                        for(int start2=0;start2<3;start2++){
                                                                            if(suduko[start][start2]==temp){
                                                                                checked=true;
                                                                                break OUTER_LOOP;
                                                                            }
                                                                            else{
                                                                                checked = false;
                                                                            }
                                                                        }
                                                                    }
                                                                  }
                                                    else if(r<3 && c>=3 && c<6){
                                                                OUTER_LOOP:
                                                                    for(int start=0;start<6;start++){
                                                                        for(int start2=3;start2<6;start2++){
                                                                            if(suduko[start][start2]==temp){
                                                                                checked=true;
                                                                                break OUTER_LOOP;
                                                                            }
                                                                            else{
                                                                                checked = false;
                                                                            }
                                                                        }
                                                                    }

                                                    }
                                                   else if(r<3 && c>=6){
                                                                                            OUTER_LOOP:
                                                                    for(int start=0;start<3;start++){
                                                                        for(int start2=6;start2<9;start2++){
                                                                            if(suduko[start][start2]==temp){
                                                                                checked=true;
                                                                                break OUTER_LOOP;
                                                                            }
                                                                            else{
                                                                                checked = false;
                                                                            }
                                                                        }
                                                                    }
                                                    }
                                                   else if(r>=3 && r<6 && c<3){
                                                                OUTER_LOOP:
                                                                    for(int start=3;start<6;start++){
                                                                        for(int start2=0;start2<3;start2++){
                                                                            if(suduko[start][start2]==temp){
                                                                                checked=true;
                                                                                break OUTER_LOOP;
                                                                            }
                                                                            else{
                                                                                checked = false;
                                                                            }
                                                                        }
                                                                    }
                                                    }
                                                   else if(r>=3 && r<6 && c>=3 && c<6){
                                                                                            OUTER_LOOP:
                                                                    for(int start=3;start<6;start++){
                                                                        for(int start2=3;start2<6;start2++){
                                                                            if(suduko[start][start2]==temp){
                                                                                checked=true;
                                                                                break OUTER_LOOP;
                                                                            }
                                                                            else{
                                                                                checked = false;
                                                                            }
                                                                        }
                                                                    }
                                                    }
                                                   else if(r>=3 && r<6 && c>=6){
                                                                                            OUTER_LOOP:
                                                                    for(int start=3;start<6;start++){
                                                                        for(int start2=6;start2<9;start2++){
                                                                            if(suduko[start][start2]==temp){
                                                                                checked=true;
                                                                                break OUTER_LOOP;
                                                                            }
                                                                            else{
                                                                                checked = false;
                                                                            }
                                                                        }
                                                                    }

                                                    }
                                                   else if(r>=6 && c<3){
                                                                                            OUTER_LOOP:
                                                                    for(int start=6;start<9;start++){
                                                                        for(int start2=0;start2<3;start2++){
                                                                            if(suduko[start][start2]==temp){
                                                                                checked=true;
                                                                                break OUTER_LOOP;
                                                                            }
                                                                            else{
                                                                                checked = false;
                                                                            }
                                                                        }
                                                                    }
                                                    }
                                                   else if(r>=6 && c>=3 && c<6){
                                                                                            OUTER_LOOP:
                                                                    for(int start=6;start<9;start++){
                                                                        for(int start2=3;start2<6;start2++){
                                                                            if(suduko[start][start2]==temp){
                                                                                checked=true;
                                                                                break OUTER_LOOP;
                                                                            }
                                                                            else{
                                                                                checked = false;
                                                                            }
                                                                        }
                                                                    }
                                                    }
                                                   else if(r>=6 && c>=6){
                                                                OUTER_LOOP:
                                                                    for(int start=6;start<9;start++){
                                                                        for(int start2=6;start2<9;start2++){
                                                                            if(suduko[start][start2]==temp){
                                                                                checked=true;
                                                                                break OUTER_LOOP;
                                                                            }
                                                                            else{
                                                                                checked = false;
                                                                            }
                                                                        }
                                                                    }
                                                    }
                                   }
                                    if(checked==false){
                                        suduko[r][c]=temp;
                                        String val = String.valueOf(temp);
                                        subPanels[r][c].setText(val);
                                    }else{
                                    }
                                }
                            }
                            this.count0=0;
                        }
                    long endTime = System.currentTimeMillis();
                    this.result.setVisible(true);
                    this.result.setText("That took " + (endTime - startTime) + " milliseconds");
                }       
        }
    /********************************************
     *  Random Values
     * @param min
     * @param max
     * @return 
    *********************************************/
    
    //********************************************
    public static int randInt(int min, int max) {
    Random rand = new Random();
    int randomNum = rand.nextInt((max - min) + 1) + min;

    return randomNum;}
    
    public final  Color setFontColor(Color input){
    Color temp;
       if(input == col[0])
           temp=col[1];
       else if(input == col[1])
           temp=col[0];
       else if(input==col[2])
           temp=col[3];
       else
           temp=col[2];
    return temp;
    }
    /***************************
     *this function is to make only the input between 0 and 9 
     * "#" means that this field accept only one digit "INELEC OU LAKHBICHE IGEE "
     * we still have problem of 0 digit ,we need to figure out this 
     * @param args 
     *****************************/
   /* protected MaskFormatter createFormatter(String s) {
    MaskFormatter formatter = null;
    try {
        formatter = new MaskFormatter(s);
    } catch (java.text.ParseException exc) {
        System.err.println("formatter is bad: " + exc.getMessage());
        System.exit(-1);
    }
    return formatter;
    }*/

public static void main(String[] args){
    @SuppressWarnings("unchecked")
        GUI gui;
        gui = new GUI();
}

}
