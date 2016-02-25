package com.smapley.baibaohe.utls;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by smapley on 2015/5/11.
 */
public class MyData {

    public static final String USERSP = "user";

    public static int UTYPE = -1;
    public static String BIAN;

    public static final String CACHE_PIC = "/Store/ImageCache/";
    public static final String URL_FILE_SUO = "http://120.25.208.188/test1/upsuo/";
    public static final String URL_FILE_YUAN = "http://120.25.208.188/test1/upload/";
    public final static String BASE_URL = "http://120.25.208.188/test1/";
    public final static String URL_GETDYPIC = BASE_URL + "getDypic.php";
    public final static String URL_GETSTOREPIC = BASE_URL + "getStorepic.php";
    public final static String URL_GETUER2 = BASE_URL + "getUser2.php";
    public final static String URL_GETNEWPIC = BASE_URL + "getNewPic.php";
    public final static String URL_GETPIC2 = BASE_URL + "getPic2.php";
    public final static String URL_GETPIC1 = BASE_URL + "getPic1.php";
    public final static String URL_GETDESCRIBE = BASE_URL + "getDes.php";
    public final static String URL_GETCOLLECT = BASE_URL + "getCollect.php";
    public final static String URL_GETRECORD = BASE_URL + "getRecord.php";
    public final static String URL_GETRECORD2 = BASE_URL + "getRecord2.php";
    public final static String URL_GETTOUXIANG = BASE_URL + "getTouxiang.php";
    public final static String URL_ADDZUOBIAO = BASE_URL + "addZuobiao.php";
    public final static String URL_ADDCOL = BASE_URL + "addCol.php";
    public final static String URL_GETALLPIC = BASE_URL + "getAllpic.php";
    public final static String URL_ADDYUYUE = BASE_URL + "addYuyue.php";
    public final static String URL_REG = BASE_URL + "reg.php";
    public final static String URL_UPDATEZILIAO = BASE_URL + "updateZiliao.php";
    public final static String URL_ADDPHOTO = BASE_URL + "addPhoto.php";
    public final static String URL_UPDATERECST = BASE_URL + "updateRecst.php";
    public final static String URL_DELDPIC = BASE_URL + "delDpic.php";
    public final static String URL_UPDATAPIC = BASE_URL + "updatePic.php";
    public final static String URL_GETJILU = BASE_URL + "getJilu.php";
    public final static String URL_GETDJ = BASE_URL + "getDj.php";
    public final static String URL_GETSOUDJ = BASE_URL + "getSoudj.php";
    public final static String URL_GETSL = BASE_URL + "getSl.php";
    public final static String URL_ADDSC = BASE_URL + "addSc.php";
    public final static String URL_UPDATEZT1 = BASE_URL + "updateZt1.php";
    public final static String URL_ADDDPIC = BASE_URL + "addDpic.php";
    public final static String URL_DELPIC = BASE_URL + "delPic.php";
    public final static String URL_GETSHOUYE = BASE_URL + "getShouye.php";
    public final static String URL_GETSHOUYE1 = BASE_URL + "getShouye1.php";
    public final static String URL_GETSOUJQ = BASE_URL + "getSoujq.php";
    public final static String URL_UPDATEJIANG = BASE_URL + "updateJiang.php";
    public final static String URL_UPDATEZT7 = BASE_URL + "updateZt7.php";
    public final static String URL_DELLIAN = BASE_URL + "delLian.php";
    public final static String URL_UPDATEZT9 = BASE_URL + "updateZt9.php";
    public final static String URL_DELJIANG = BASE_URL + "delJiang.php";
    public final static String URL_GETGFPIC = BASE_URL + "getGfpic.php";
    public final static String URL_GETDYLIST = BASE_URL + "getDylist.php";
    public final static String URL_DELDYLIST = BASE_URL + "delDylist.php";
    public final static String URL_ADDDYLIST = BASE_URL + "addDylist.php";
    public final static String URL_ADDDJIANG = BASE_URL + "addJiang.php";
    public final static String URL_GETLISHI = BASE_URL + "getLishi.php";
    public final static String URL_GETSJDP = BASE_URL + "getSjdp.php";
    public final static String URL_GETMOHUDP = BASE_URL + "getMohudp.php";
    public final static String URL_ADDGOLD = BASE_URL + "addGold.php";
    public final static String URL_GETGOLD = BASE_URL + "getGold.php";
    public final static String URL_ADDCHONGZHI = BASE_URL + "addChongzhi.php";
    public final static String URL_ADDFUKUAN = BASE_URL + "addFukuan.php";
    public final static String URL_GETSOUSK = BASE_URL + "getSousk.php";
    public final static String URL_GETTIXIAN = BASE_URL + "getTixian.php";
    public final static String URL_GETSHOUJILU = BASE_URL + "getShoujilu.php";
    public final static String URL_GETFUJILU = BASE_URL + "getFujilu.php";
    public final static String URL_ADDTIXIAN = BASE_URL + "addTixian.php";
    public final static String URL_UPDATESK = BASE_URL + "updateSk.php";
    public final static String URL_YANZHENG = BASE_URL + "yanzheng1.php";
    public final static String URL_ADDLIAN = BASE_URL + "addLian.php";
    public final static String URL_UPDATETUIKUAN = BASE_URL + "updateTuikuan.php";
    public final static String URL_GETLIAN = BASE_URL + "getLian.php";
    public final static String URL_GETJP = BASE_URL + "getJp.php";
    public final static String URL_GETLIANLB = BASE_URL + "getLianlb.php";
    public final static String URL_GETLIANLB1 = BASE_URL + "getLianlb1.php";
    public final static String URL_GETFUJILU1 = BASE_URL + "getFujilu1.php";
    public final static String URL_UPDATELIANZT1 = BASE_URL + "updateLianzt1.php";
    public final static String URL_GETJILUZK = BASE_URL + "getJiluzk.php";
    public final static String URL_ADDHUAN = BASE_URL + "addHuan.php";
    public final static String URL_GETDYLIST1 = BASE_URL + "getDylist1.php";
    public final static String URL_PING = BASE_URL + "ping.php";

    public static int imagechangeed = 0;
    public static String PHONE;


    public final static int PUBU_ZHANSHI = 1;

    public final static int LOGINRESULT = 1;
    public static boolean CANDELECT = false;

    public static String STORE_ID;
    public static String STORE_NM;
    public static String USER2;

    //    ��textview�е��ַ�ȫ�ǻ����������е����֡���ĸ�����ȫ��תΪȫ���ַ�
    // ʹ�����뺺��ͬռ�����ֽڣ�����Ϳ��Ա�������ռλ���µ��Ű���������ˡ�
    // ���תΪȫ�ǵĴ������£�ֻ����ü��ɡ�
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    public static String ToSBC(String input) {
        char c[] = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == ' ') {
                c[i] = '\u3000';
            } else if (c[i] < '\177') {
                c[i] = (char) (c[i] + 65248);
            }
        }
        return new String(c);
    }

    /**
     * ȥ�������ַ���������ı���滻ΪӢ�ı��
     *
     * @param str
     * @return
     */
    public static String stringFilter(String str) {
        str = str.replaceAll("��", "[").replaceAll("��", "]")
                .replaceAll("��", "!").replaceAll("��", ":");// �滻���ı��
        String regEx = "[����]"; // ���������ַ�
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }
}
