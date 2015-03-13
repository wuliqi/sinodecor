package cn.app118.action.login;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.app118.action.common.BaseAction;
import cn.app118.constants.SystemConstant;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

@Controller
@RequestMapping("/verificationCode")
public class VerificationCodeAction extends BaseAction{
    /**
     * 设置字体
     */
    private Font mFont = new Font("Arial Black", Font.PLAIN, 15);
    /**
     * 干扰线的长度
     */
    private int lineWidth = 2; 
    /**
     * 生成校验码图形的宽度
     */
    private int width = 60; 
    /**
     * 生成校验码图形的高度
     */
    private int height = 20;
    /**
     * 生成干扰线的个数
     */
    private int count = 200;
    
    /**
     * 获取验证码
     */
    @RequestMapping("/getImgCode")
    public String getImgCode(HttpServletResponse response){
        //必须设置ContentType为image/jpeg
        response.setContentType("image/jpeg;charset=utf-8"); 
        //设置页面不缓存
        response.setHeader ("Pragma", "No-cache");
        response.setHeader ("Cache-Control", "no-cache");
        response.setDateHeader ("Expires", 0);
        response.setContentType ("image/gif");
    
        //在内存中创建图象
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //获取图形上下文
        Graphics2D g = bufferedImage.createGraphics();
    
        //生成随机类
        Random random = new Random();
        //设定背景色
        g.setColor(getRandColor(200,250));
        g.fillRect(0, 0, width, height);
        
        //设定字体黑色文字
        g.setFont(mFont);
        g.setColor(Color.black);
        
        //画边框
        g.setColor (getRandColor (0, 20)); 
        g.drawRect (0, 0, width - 1, height - 1);
    
        //随机产生干扰线，使图象中的认证码不易被其它程序探测到
        for (int i = 0; i < count; i++) {
            g.setColor (getRandColor (150, 200));
            //保证画在边框之内
            int x = random.nextInt (width - lineWidth - 1) + 1; 
            int y = random.nextInt (height - lineWidth - 1) + 1;
            int xl = random.nextInt (lineWidth);
            int yl = random.nextInt (lineWidth);
            g.drawLine (x, y, x + xl, y + yl);
        }
    
        //取随机产生的认证码(4位数字)
        String sRand = "";

        for (int i = 0; i < 4; i++) {
        	//如果功能不需要设定固定验证码，则将下面这行代码放开
            String rand = String.valueOf (random.nextInt (10));
            //如果功能需要设定固定验证码，则将下面这行代码放开，固定为8888
            //String rand = "8";
            sRand += rand;
            //将认证码显示到图象中,调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
            g.setColor (new Color(20 + random.nextInt (130),
            20 + random.nextInt (130), 20 + random.nextInt (130)));
            g.drawString (rand, (13 * i) + 6, 16);
        }
        HttpSession httpSession = request.getSession();
        //将认证码存入SESSION
        httpSession.setAttribute (SystemConstant.VERIVYWORD, sRand);
        
        //图象生效
        g.dispose ();
        try {
            //使用JPEG编码，输出到response的输出流
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(response.getOutputStream());
            JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bufferedImage);
            param.setQuality(1.0f, false);
            encoder.setJPEGEncodeParam(param);
            encoder.encode(bufferedImage);
        }catch (Exception e) {
           log.info("execute"+e);
        	
        }
        return null;
    }
    
    /**
     * 给定范围获得随机RGB颜色
     */
    private Color getRandColor(int pigmentA, int pigmentB) {
        Random random = new Random();
        if (pigmentA > 255)
            pigmentA = 255;
        if (pigmentB > 255)
            pigmentB = 255;
        int r = pigmentA + random.nextInt(pigmentB - pigmentA);
        int g = pigmentA + random.nextInt(pigmentB - pigmentA);
        int b = pigmentA + random.nextInt(pigmentB - pigmentA);
        return new Color(r, g, b);
    }
    
    /**
     * AIAX方式校验验证码
     */
    @RequestMapping("/checkVerifyWord")
    @ResponseBody
    public boolean checkVerifyWord(String verifyWord){
        boolean  res = false;
        HttpSession httpSession = request.getSession();
        String word = (String)httpSession.getAttribute(SystemConstant.VERIVYWORD);
        if(verifyWord != null && verifyWord.length() != 0){
            if(verifyWord.equals(word)){
                res = true;
            }
        }
        return res;
    }
}
