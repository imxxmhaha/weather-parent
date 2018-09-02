
package cn.xxm.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用正则表达式过滤html文本的工具类.
 *
 * @author daice
 */
public class RegexUtil {

    private static final Pattern pat1ScriptTag;
    private static final Pattern pat2ScriptTag;
    private static final Pattern pat3ScriptTag;
    //	private static final Pattern patIframeTag;
    private static final Pattern pat1IframeTag;
    private static final Pattern pat2IframeTag;
    private static final Pattern pat3IframeTag;

    private static final Pattern patJSProtocol;
    private static final Pattern patEventCode;
    private static final Pattern patInjectOfImg;
    private static final Pattern patInjectOfInput;

    /**
     * 获取用正则表达式过滤后的安全html文本. 该方法包含以下处理:
     * <li>去除script标记的内容
     * <li>去除iframe标记的内容
     * <li>将javascript:替换为java-script:
     * <li>将onXXX替换为on-XXX:
     *
     * @param text 需要过滤的安全html文本. 如果该参数为null或空串, 则仍返回该参数.
     */
    public static String getSafeHtml(String text) {
        if (text == null || text.trim().length() == 0) {
            return text;
        }

        text = removeScriptTag(text);
        text = removeIframeTag(text);
        text = replaceEventCode(text);
        text = replaceJS(text);
        text = replaceInjectOfImg(text);
        text = replaceInjectOfInput(text);

        return text;
    }

    static String removeScriptTag(String text) {
        String replacementStr = "";
        Matcher matcher = pat1ScriptTag.matcher(text);

        String result = matcher.replaceAll(replacementStr);

        matcher = pat2ScriptTag.matcher(result);
        result = matcher.replaceAll(replacementStr);

        matcher = pat3ScriptTag.matcher(result);
        result = matcher.replaceAll(replacementStr);
        return result;
    }

    static String removeIframeTag(String text) {
        String replacementStr = "";

        Matcher matcher = pat1IframeTag.matcher(text);
        text = matcher.replaceAll(replacementStr);

        matcher = pat2IframeTag.matcher(text);
        text = matcher.replaceAll(replacementStr);

        matcher = pat3IframeTag.matcher(text);
        return matcher.replaceAll(replacementStr);
    }

    static String replaceEventCode(String text) {
        String replacementStr = "$1on-$2=";
        Matcher matcher = patEventCode.matcher(text);
        return matcher.replaceAll(replacementStr);
    }

    static String replaceJS(String text) {
        String replacementStr = "=$2java-script:";
        Matcher matcher = patJSProtocol.matcher(text);
        return matcher.replaceAll(replacementStr);
    }


    /**
     * 去除img中代码反向注入的情况，规则是过滤掉所有img .do的字符串
     *
     * @param text
     * @return
     */
    static String replaceInjectOfImg(String text) {
        String replacementStr = "";
        Matcher matcher = patInjectOfImg.matcher(text);
        return matcher.replaceAll(replacementStr);
    }

    static String replaceInjectOfInput(String text) {
        String replacementStr = "";
        Matcher matcher = patInjectOfInput.matcher(text);
        return matcher.replaceAll(replacementStr);

    }


    static {

        // ls@07-0528 Note: pat1, 2, 3顺序不能反!
        pat1ScriptTag = Pattern.compile("<script\\s*[^>]*>.*</script>", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        pat2ScriptTag = Pattern.compile("<script\\s*[^>]*/>", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        pat3ScriptTag = Pattern.compile("<script\\s*[^>]*>", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

        pat1IframeTag = Pattern.compile("<iframe\\s*[^>]*>.*</iframe>", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        pat2IframeTag = Pattern.compile("<iframe\\s*[^>]*/>", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        pat3IframeTag = Pattern.compile("<iframe\\s*[^>]*>", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

        patJSProtocol = Pattern.compile("=(\\s)*(\\'|\\\")?(\\s)*javascript:", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
        patEventCode = Pattern.compile("(\\s)on([a-zA-Z]+)\\s*=", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
        patInjectOfImg = Pattern.compile("<\\s*img[^>]*\\.do[^>]*>", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
        patInjectOfInput = Pattern.compile("<\\s*input[^>]*src=[^>]*\\.do[^>]*>", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);

		/*
		// ls@07-0528 (x\y)* may cause StackOverFlowError. see http://madbean.com/2004/mb2004-20/ and http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=5050507
		// patScriptTag = Pattern.compile("<script\\s*(.|\\s)*(/>|>(.|\\s)*</script>)", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
		// patIframeTag = Pattern.compile("<iframe\\s*(.|\\s)*(/>|>(.|\\s)*</iframe>)", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
    	*/
    }

}
