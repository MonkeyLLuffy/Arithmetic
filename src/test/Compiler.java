package test;


import java.awt.*;
import java.awt.event.*;
import java.io.*;
import  javax.swing.*;
import javax.swing.JOptionPane;

public class Compiler extends JFrame implements ActionListener{
    int row = 1;
    int line = 1;
    int err=0;
    JMenuBar mb = new JMenuBar();
    JMenu fileMenu = new JMenu("文件(F)");
    JMenu actionMenu = new JMenu("词法分析(W)");
    JMenuItem closeWindow = new JMenuItem("退出");
    JMenuItem openFile = new JMenuItem("打开");
    JMenuItem lexical_check = new JMenuItem("开始");
    int begin = 0;
    int end = 0;
    TextArea text = new TextArea();
    TextArea error_text = new TextArea();
    TextArea end_text = new TextArea();
    FileDialog file_dialog_load = new FileDialog(this, "Open file...", FileDialog.LOAD);
    JPanel pan1=new JPanel();
    JPanel pan2=new JPanel();
    
    Compiler(){
        this.add(end_text);end_text.setEditable(false);
        this.add(text);
        this.add(error_text);error_text.setEditable(false);
        pan1.setLayout(new GridLayout(1,1)); pan1.add(text);
        pan2.setLayout(new GridLayout(2,1));
        pan2.add(error_text,"North");
        pan2.add(end_text,"South");
        getContentPane().add(pan1,"West");
        getContentPane().add(pan2,"Center");
        this.setJMenuBar(mb);
        mb.add(fileMenu);
        mb.add(actionMenu);
        fileMenu.add(openFile);
        fileMenu.add(closeWindow);
        actionMenu.add(lexical_check);
        error_text.setText("----------------------------------------------词法分析结果-------------------------------------------\n");
        end_text.setText("--------------------------------------------词法分析错误信息-------------------------------------- \n");
        closeWindow.addActionListener(this);
        openFile.addActionListener(this);
        lexical_check.addActionListener(this);
        pack();
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });
        this.setVisible(true);
    }
    
    public static void main(String[] args) {
        Compiler compiler = new Compiler();
    }
    
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == closeWindow) {
            int flag = JOptionPane.showConfirmDialog(null, "是否退出");
            System.out.println("flag=" + flag);
            if (flag == 0) {
                System.exit(0);
            } else if (flag == 1) {
             
            }
        } else if(e.getSource() == openFile){
            file_dialog_load.setVisible(true);
            File myfile = new File(file_dialog_load.getDirectory(), file_dialog_load.getFile());
            try{
                BufferedReader bufReader = new BufferedReader(new FileReader(myfile));
                String content = "";
                String str;
                
                while((str = bufReader.readLine()) != null){
                    content += str + "\n";
                    text.setText(content);
                }
            }catch(IOException ie){
                System.out.println("IOexception occurs...");
            }
        }else if(e.getSource() == lexical_check){
            error_text.setText("");
            row = 0;
            line = 1;
            checkLexical();
        }
    }
    
    public void checkLexical(){
        String error_info = error_text.getText();
        String content = text.getText();
        if(content.equals("")){
            error_info += "文 件 尚 未 载 入 ！\n";
            error_text.setText(error_info);
        } else{
            int i = 0;//选择第i个字符进行检测。
            int N = content.length();//文件大小
            
            int state = 0;//状态标志
            for(i = 0; i < N; i++){//对所有字符进行检测
                row++;
                char c = content.charAt(i);
                switch(state){
                    case 0:
                        if(c == ',' || c == ' ' || c == '\t' || c == '{' || c =='}' || c == '(' ||
                                c == ')' || c == ';' || c == '[' || c == ']') {
                            if(isDigit(content.charAt(i - 1)) && isDigit(content.charAt(begin))){
                                end = i;
                                error_text.append("line:"+line+"\t数  值\t" +
                                        content.substring(begin, end) + '\n');
                            }
                            if(c==',') error_text.append("line:"+line+"\t界  符\t"+","+'\n');
                            if(c==';') error_text.append("line:"+line+"\t界  符\t"+";"+'\n');
                            if(c=='{') error_text.append("line:"+line+"\t界  符\t"+"{"+'\n');
                            if(c=='}') error_text.append("line:"+line+"\t界  符\t"+"}"+'\n');
                            if(c=='(') error_text.append("line:"+line+"\t界  符\t"+"("+'\n');
                            if(c==')') error_text.append("line:"+line+"\t界  符\t"+")"+'\n');
                            if(c=='[') error_text.append("line:"+line+"\t界  符\t"+"["+'\n');
                            if(c==']') error_text.append("line:"+line+"\t界  符\t"+"]"+'\n');
                            state = 0;
                        } else if(c == '+') state = 1;
                        else if(c == '-') state = 2;
                        else if(c == '*') state = 3;
                        else if(c == '/') state = 4;
                        else if(c == '!') state = 5;
                        else if(c == '>') state = 6;
                        else if(c == '<') state = 7;
                        else if(c == '=') state = 8;
                        else if(c=='.') state=17;
                        else if(c=='~') state=18;
                        else if(c=='%') state=19;
                        else if(c=='^') state=20;
                        else if(((int)c) == 10) {
                            state = 9;//输入为回车
                            //error_text.append("Line"+line+"\n\n");
                        } else if(isLetter(c)) {
                            state = 10;
                            begin = i;
                        }
                        
                        else if(isDigit(c)) {
                            begin = i;
                            state = 11;
                        } else if(c == '#') state = 12;
                        else if(c == '&') state = 14;
                        else if(c == '|') state = 15;
                        else if(c == '"') state = 16;
                        else {
                            err++;
                            String a=end_text.getText();
                            a+="错误: line: " + line + "   row: " + row + " error: '" + c + "' Undefined character! \n";
                            end_text.setText(a);
                            //error_text.append("错误: line: " + line + "   row: " + row + " error: '" + c + "' Undefined character! \n");
                        }
                        break;
                    case 1://标志符是 +
                        
                        if(c == '+'){
                            state = 0;
                            error_text.append("line:"+line+"\t运算符\t"+"++"+'\n');
                        } else if(c == '='){
                            state = 0;
                            error_text.append("line:"+line+"\t运算符\t "+"\t+="+'\n');
                            
                        }else{
                            state = 0;
                            if(isDigit(content.charAt(i - 2)))
                                error_text.append("line:"+line+"\t数  值\t" +
                                        content.substring(begin, i-1) + '\n');
                            error_text.append("line:"+line+"\t运算符\t"+"+"+'\n');
                            i--;
                            row--;
                        }
                        break;
                    case 2://标志符是 -
                        if(c == '-')
                            error_text.append("line:"+line+"\t运算符\t\t404 "+"\t--"+'\n');
                        else if(c == '=')
                            error_text.append("line:"+line+"\t运算符\t\t405 "+"\t-="+'\n');
                        else if(c=='>')
                            error_text.append("line:"+line+"\t运算符\t\t423  "+"\t->"+'\n');
                        else{
                            error_text.append("line:"+line+"\t运算符\t\t406  "+"\t-"+'\n');
                            i--;
                            row--;
                        }
                        state = 0;
                        break;
                    case 3://标志符是 *
                        if(c == '=')
                            error_text.append("line:"+line+"\t运算符\t "+"*="+'\n');
                        else{
                            error_text.append("line:"+line+"\t运算符\t"+"*"+'\n');
                            i--;
                            row--;
                        }
                        state = 0;
                        break;
                    case 4://标志符是 /
                        if(c == '/'){
                            while((c) != '\n'){
                                c = content.charAt(i);
                                i++;
                            }
                            state = 0;
                            error_text.append("line:"+line+"\t注释部分\t\t// \n");
                        }else if(c == '='){
                            state = 0;
                            error_text.append("line:"+line+"\t运算符\t"+"/="+'\n');
                        }else{
                            state = 0;
                            error_text.append("line:"+line+"\t运算符\t"+"/"+'\n');
                            i--;
                            row--;
                        }
                        //state = 0;
                        break;
                    case 5://标志符是 !
                        if(c == '='){
                            error_text.append("line:"+line+"\t运算符\t\t411 "+"\t!="+'\n');
                            state = 0;
                        }else{
                            state = 0;
                            i--;
                            row--;
                            error_text.append("line:"+line+"\t运算符\t"+"!"+'\n');
                        }
                        
                        break;
                    case 6://标志符是 >
                        if(c == '='){
                            error_text.append("line:"+line+"\t运算符\t"+">="+'\n');
                            state = 0;
                        }
                        if(c=='>') {
                            error_text.append("line:"+line+"\t运算符\t"+">>"+'\n');
                            state=0;
                        } else{
                            state = 0;
                            i--;
                            row--;
                            error_text.append("line:"+line+"\t运算符\t"+">"+'\n');
                        }
                        //state = 0;
                        break;
                    case 7://标志符是 <
                        if(c == '='){
                            error_text.append("line:"+line+"\t运算符\t"+"<="+'\n');
                            state = 0;
                        }
                        if(c=='<') {
                            error_text.append("line:"+line+"\t运算符\t"+"<<"+'\n');
                            state=0;
                        } else{
                            state = 0;
                            i--;
                            row--;
                            error_text.append("line:"+line+"\t运算符\t"+"<"+'\n');
                        }
                        break;
                    case 8://标志符是 =
                        if(c == '='){
                            error_text.append("line:"+line+"\t运算符\t"+"=="+'\n');
                            state = 0;
                        }else{
                            state=0;
                            i--;
                            row--;
                            error_text.append("line:"+line+"\t运算符\t"+"="+'\n');
                        }
                        break;
                    case 9://标志符是 回车
                        state = 0;
                        i--;
                        row = 1;
                        line ++;
                        break;
                    case 10://标志符是 字母
                        if(isLetter(c) || isDigit(c)){
                            state = 10;
                        }else{
                            end = i;
                            String id = content.substring(begin, end);
                            if(isKey(id)!=0) {
                                error_text.append("line:"+line+"\t关键字\t"+id + '\n');
                            } else
                                error_text.append("line:"+line+"\t标志符\t" +id + '\n');
                            i--;
                            row--;
                            state = 0;
                        }
                        //state = 0;
                        break;
                    case 11://标志符是 数字
                        if(c == 'e' || c == 'E')
                            state = 13;
                        else if(isDigit(c) || c == '.'){
                            //省略跳过，i加一操作
                        }else {
                            if(isLetter(c)){
                                err++;
                                String b=end_text.getText();
                                b+="错误: line " + line + " row " + row + " 数字格式错误或者标志符错误\n";
                                end_text.setText(b);
                                //error_text.append("错误:  line " + line + " row " + row + " 数字格式错误或者标志符错误\n");
                            }
                            int temp = i;
                            i = find(i,content);
                            row += (i - temp);
                            state = 0;
                        }
                        
                        break;
                    case 12://标志符是 #
                        String id = "";
                        while(c != '<'){
                            id += c;
                            i++;
                            c = content.charAt(i);
                        }
                        if(id.trim().equals("include")){
                            while(c != '>' && ( c != '\n')){
                                i++;
                                c = content.charAt(i);
                            }
                            if(c == '>')
                                error_text.append("\t头文件引入 \n");
                        }else {
                            err++;
                            String  d=end_text.getText();
                            d+="错误: " + "line " + line + ", row " + row + " 语法错误\n";
                            end_text.setText(d);
                            //error_text.append("错误: " + "line " + line + ", row " + row + " 语法错误\n");
                        }
                        
                        state = 0;
                        break;
                    case 13://检测指数表示方式
                        if(c == '+' || c == '-' || isDigit(c)){
                            i++;
                            c = content.charAt(i);
                            while(isDigit(c)){
                                i++;
                                c = content.charAt(i);
                            }
                            if(isLetter(c) || c == '.') {
                                err++;
                                String e=end_text.getText();
                                e+="错误:  line " + line + "   row " + row + " 指数格式错误！\n";
                                end_text.setText(e);
                                //error_text.append("错误:  line " + line + " row " + row + " 指数格式错误！\n");
                                state = 0;
                                int temp = i;
                                i = find(i,content);
                                row += (i - temp);
                                //error_text.appendText("i = " + i + " row = " + row + " len = " + content.length() + '\n');
                            }else{
                                end = i;
                                error_text.append("line:"+line+"\t指  数\t\t" + content.substring(begin, end) + '\n');
                            }
                            state = 0;
                        }
                        break;
                    case 14:// 逻辑运算发 &&
                        if(c == '&'){
                            if(isDigit(content.charAt(i - 2)))
                                error_text.append("line:"+line+"\t数  值\t" +"\t200  "+
                                        content.substring(begin, i-1) + '\n');
                            error_text.append("line:"+line+"\t运算符\t"+"&&"+ "\n");
                        }
                        
                        else{
                            i--;
                            error_text.append("line:"+line+"\t运算符\t"+"&"+ "\n");
                        }
                        state = 0;
                        break;
                    case 15:// 逻辑运算发 ||
                        if(c == '|') {
                            if(isDigit(content.charAt(i - 2)))
                                error_text.append("line:"+line+"\t数  值\t" +
                                        content.substring(begin, i-1) + '\n');
                            error_text.append("line:"+line+"\t运算符\t"+"\t||"+ "\n");} else{
                            i--;
                            error_text.append("line:"+line+"\t运算符\t"+"\t|"+ "\n");
                            }
                        state = 0;
                        break;
                    case 16:
                        error_text.append("line:"+line+"\t界  符\t" + '"' + '\n');
                        i--;
                        state = 0;
                        break;
                    case 17:
                        error_text.append("line:"+line+"\t运算符\t" + '.' + '\n');
                        i--;
                        state=0;
                        break;
                    case 18:
                        error_text.append("line:"+line+"\t运算符\t" + '~' + '\n');
                        i--;
                        state=0;
                        break;
                    case 19:
                        if(c=='=') {
                            error_text.append("line:"+line+"\t运算符\t" + "%=" + '\n');
                            state=0;
                        } else{
                            state=0;
                            i--;
                            row--;
                            error_text.append("line:"+line+"\t运算符\t"+"%"+'\n');
                        }
                        break;
                    case 20:
                        error_text.append("line:"+line+"\t运算符\t" + '^' + '\n');
                        i--;
                        state=0;
                        break;
                }
            }
        }
        error_text.append("词 法 分 析 结 束 ! \n");
        //  end_text.append("----------------------------错误------------------------ \n");
        end_text.append(err+" errors \n");
        
    }
    
    
    
    boolean isLetter(char c){
        if((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_')
            return true;
        return false;
    }
    
    
    boolean isDigit(char c){
        if(c >= '0' && c <= '9') return true;
        return false;
    }
    
    
    int isKey(String str){
        if(str.equals("auto"))  return 1;
        if(str.equals("break")) return 2;
        if(str.equals("case")) return 3;
        if(str.equals("char")) return 4;
        if(str.equals("const")) return 5;
        if(str.equals("return")) return 6;
        if(str.equals("continue")) return 7;
        if(str.equals("default")) return 8;
        if(str.equals("do")) return 9;
        if(str.equals("double")) return 10;
        if(str.equals("else")) return 11;
        if(str.equals("struct")) return 12;
        if(str.equals("enum")) return 13;
        if(str.equals("extern")) return 14;
        if(str.equals("float")) return 15;
        if(str.equals("for")) return 16;
        if(str.equals("goto")) return 17;
        if(str.equals("if")) return 18;
        if(str.equals("int")) return 19;
        if(str.equals("long")) return 20;
        if(str.equals("register")) return 21;
        if(str.equals("void")) return 22;
        if(str.equals("short")) return 23;
        if(str.equals("signed")) return 24;
        if(str.equals("sizeof")) return 25;
        if(str.equals("static")) return 26;
        if(str.equals("switch")) return 27;
        if(str.equals("typedef")) return 28;
        if(str.equals("union")) return 29;
        if(str.equals("unsigned")) return 30;
        if(str.equals("volatile")) return 31;
        if(str.equals("while")) return 32;
        else return 0;
    }
    
    
    int find(int begin, String str){//寻找分隔符空格、括号、回车等。
        if(begin >= str.length())
            return str.length();
        for(int i = begin; i < str.length(); i++){
            char c = str.charAt(i);
            if(c == '\n' || c == ',' || c == ' ' || c == '\t' || c == '{' || c =='}' || c == '(' || c == ')' || c == ';' || c == '=' || c == '+'|| c == '-'
                    || c == '*' || c == '/'||c=='&'&&str.charAt(i+1)=='&'||c=='|'&&str.charAt(i+1)=='|'||c=='['||c==']') {
                return i - 1;
            }
            
        }
        return str.length();
    }
    
}
