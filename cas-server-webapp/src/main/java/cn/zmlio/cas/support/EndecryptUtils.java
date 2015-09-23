package cn.zmlio.cas.support;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.sun.crypto.provider.AESKeyGenerator;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.CodecSupport;
import org.apache.shiro.codec.H64;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;

import java.security.Key;

/**
 * Created by ZML on 2015/9/22.
 */
public class EndecryptUtils {
    /**
     * base64进制加密
     *
     * @param password
     * @return
     */
    public static String encrytBase64(String password) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(password), "不能为空");
        byte[] bytes = password.getBytes();
        return Base64.encodeToString(bytes);
    }

    /**
     * base64进制解密
     *
     * @param cipherText
     * @return
     */
    public static String decryptBase64(String cipherText) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(cipherText), "消息摘要不能为空");
        return Base64.decodeToString(cipherText);
    }

    /**
     * 16进制加密
     *
     * @param password
     * @return
     */
    public static String encrytHex(String password) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(password), "不能为空");
        byte[] bytes = password.getBytes();
        return Hex.encodeToString(bytes);
    }

    /**
     * 16进制解密
     *
     * @param cipherText
     * @return
     */
    public static String decryptHex(String cipherText) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(cipherText), "消息摘要不能为空");
        return new String(Hex.decode(cipherText));
    }

    public static String generateKey() {
        AesCipherService aesCipherService = new AesCipherService();
        Key key = aesCipherService.generateNewKey();
        return Base64.encodeToString(key.getEncoded());
    }

    /**
     * 对密码进行md5加密,并返回密文和salt，包含在User对象中
     *
     * @param username 用户名
     * @param password 密码
     * @return 密文和salt
     */
    public static SSOPrincipal md5Password(String username, String password) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(username), "username不能为空");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(password), "password不能为空");
        SecureRandomNumberGenerator secureRandomNumberGenerator = new SecureRandomNumberGenerator();
        String salt = secureRandomNumberGenerator.nextBytes().toHex();
        //组合username,两次迭代，对密码进行加密
        String password_cipherText = new Md5Hash(password, username + salt, 2).toBase64();
        System.out.println(salt+","+password_cipherText);

        SSOPrincipal user = new SSOPrincipal();
//        user.setPassword(password_cipherText);
//        user.setSalt(salt);
//        user.setUsername(username);
        return user;
    }
    public static void main(String...args){
        md5Password("admin","admin");
    }

}
