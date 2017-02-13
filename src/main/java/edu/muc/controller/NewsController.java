/**
 *
 */
package edu.muc.controller;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.core.ActionKey;
import com.jfinal.upload.UploadFile;

import edu.muc.controller.interceptor.AuthInterceptor;
import edu.muc.controller.interceptor.AuthenticationInterceptor;
import edu.muc.controller.plugin.Controller;
import edu.muc.model.Article;
import edu.muc.model.Title;
import edu.muc.model.Type;
import edu.muc.model.UserInfo;
import edu.muc.platform.plugin.DictKeys;
import edu.muc.platform.plugin.tools.SystemTools;
import edu.muc.service.NewsService;
import edu.muc.service.UserInfoService;

/**
 * @author 龚文东
 *         <p>
 *         <p>
 *         2015年6月1日 上午10:38:05
 */
@SuppressWarnings("unused")
@Controller(controllerKey = {"/news"})
@Before(AuthenticationInterceptor.class)
public class NewsController extends BaseController {
    @ActionKey("/news/logo/config")
    public void logoConfig() {
        String mHttpUrl = SystemTools.getWebRootPath() + "\\upload\\logo";
        int maxSize = 10 * 1024 * 1024;              //10M
        UploadFile uploadFile = getFile("file", mHttpUrl, maxSize, "utf-8");
        String[] extension = uploadFile.getFileName().split("\\.");
        String originalFilename = uploadFile.getOriginalFileName();
        File file = uploadFile.getFile();
        String id = getPara("id");
        String target = getPara("target");
        File temp = new File(mHttpUrl + "\\" + id + "." + extension[extension.length - 1]);
        if (temp.isFile()) {
            temp.delete();
        }
        file.renameTo(temp);
        System.out.println(id + "####" + uploadFile.toString() + "this is temp:" + temp + "\n this is the extention:" + extension[extension.length - 1]);
        Title title = NewsService.getInstance().findTitleById(target, id);
        title.setLogo("/upload/logo/" + id + "." + extension[extension.length - 1]);
        NewsService.getInstance().updateTitle(title);
        setAttr("urlState", "logo-config");
        render("/platform/index.html");

    }

    /*
     * param:target-id
     */
    @ActionKey("/news/title/modify")
    public void titleModify() {
        String target = getPara(0);
        String id = getPara(1);
        String action = getPara(2) + "-" + getPara(3);
        Title title = NewsService.getInstance().findTitleById(target, id);
        setAttr("title", title);
        setAttr("action", action);
        render("/platform/news/title_modify.html");


    }

    @ActionKey("/news/modify")
    public void newModify() {
        String target = getPara(0);
        String id = getPara(1);
        String action = getPara(2) + "-" + getPara(3);
        Title title = NewsService.getInstance().findTitleById(target, id);
        Article article = NewsService.getInstance().getArticle(id);

        setAttr("title", title);
        setAttr("action", action);
        setAttr("article", article);
        render("/platform/news/modify.html");
    }

    //here does not compete
    @ActionKey("/news/type/title/modify")
    public void newTypeModify() {
        System.out.println(new Date());
        String target = getPara(0);
        String id = getPara(1);
        String action = getPara(2) + "-" + getPara(3);
        Title title = NewsService.getInstance().findTypeTitleById(target, id);
        Article article = NewsService.getInstance().getArticle(id);
        setAttr("title", title);
        setAttr("target", target);
        setAttr("article", article);

        render("/platform/news/type_title_modify.html");
    }

    @ActionKey("/modify/news")
    public void modifyNews() {
        String action = getPara("action");
        Title title = new Title();
        Article article = new Article();
        this.setArticle(article);
        this.setTitle(title);
        article.setId(title.getId());
        article.setDescription(title.getDescription());
        NewsService.getInstance().updateTitle(title);
        NewsService.getInstance().updateArticle(article);
        setAttr("urlState", action);
        render("/platform/index.html");
    }

    @ActionKey("/modify/type/news")
    public void modifyTypeNews() {
        String target = getPara("target");
        Title title = new Title();
        Article article = new Article();
        this.setArticle(article);
        this.setTitle(title);
        article.setId(title.getId());
        article.setDescription(title.getDescription());
        NewsService.getInstance().updateTypeTitle(target, title);
        NewsService.getInstance().updateArticle(article);
        setAttr("urlState", "news-config");
        render("/platform/index.html");
    }

    @ActionKey("/news/modify/title")
    public void modifyTitle() {
        String action = getPara("action");
        Title title = new Title();
        Article article = new Article();
        this.setArticle(article);
        this.setTitle(title);
        article.setId(title.getId());
        article.setDescription(title.getDescription());
        NewsService.getInstance().updateTitle(title);
        NewsService.getInstance().updateArticle(article);
        setAttr("urlState", action);
        render("/platform/index.html");
    }

    @ActionKey("/news/config")
    public void newsConfig() {
        String newsId = getPara("id");
        String typeName = getPara("type");
        String target = getPara("action");
        Title title = NewsService.getInstance().findTitleById(target, newsId);
        title.setLinkId(typeName);//this is to set the title linkId <--> the typeName;
        NewsService.getInstance().saveTitleByType(typeName, title);
        setAttr("urlState", "news-config");
        render("/platform/index.html");
    }

    @ActionKey("/title/config")
    public void titleConfig() {

    }

    public void preAdd() {
        List<Type> type = NewsService.getInstance().getAllType();
        setAttr("theNewsType", type);
        AuthInterceptor.authSave(getSession(), UserInfoService.dao
                .findById(((UserInfo) getSession().getAttribute("theUserInfo"))
                        .getId()));
        setAttr("urlState", "news-add");
        renderJson("list", type);
    }

    @ActionKey("/news/type/title")
    public void viewTypeTitle() {
        String target = this.getPara("target");
        String sEcho = this.getPara("sEcho");
        int iDisplayLength = Integer.parseInt(getPara("iDisplayLength"));
        int iDisplayStart = Integer.parseInt(getPara("iDisplayStart"));
        List<Title> titles = null;
        List<Title> temp = new ArrayList<Title>();
        if (target != null) {
            titles = NewsService.getInstance().viewTitles("\\Type", target);
            int i = 0;
            while (i < iDisplayLength && iDisplayStart < titles.size()) {
                temp.add(titles.get(iDisplayStart));
                iDisplayStart++;
                i++;
            }
        }
        if (titles == null) {
            Map map = new HashMap();
            map.put("list", "");
            map.put("iTotalRecords", titles.size());
            map.put("iTotalDisplayRecords", titles.size());
            map.put("sEcho", sEcho);
            this.renderJson(map);
            return;
            /*titles=new ArrayList<Title>();
			Title title=new Title("", "404! cannot find!", "无", "无",
					"无", "无", null,
					null, 0, "无");
			titles.add(title);
			temp=titles;*/
        }

        Map map = new HashMap();
        map.put("list", temp);
        map.put("iTotalRecords", titles.size());
        map.put("iTotalDisplayRecords", titles.size());
        map.put("sEcho", sEcho);
        this.renderJson(map);
    }

    public void viewNews() {
        String action = this.getPara("action");
        String month = this.getPara("month");
        Calendar calendar = Calendar.getInstance();
        if (month != null && month.length() == ("YYYYMM").length()) {
            try {
                calendar.setTime(DictKeys.monthDateFormat.parse(month));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
	/*	logger.debug(calendar.toString());
		int aYear = calendar.get(calendar.YEAR);
		int aMonth = calendar.get(calendar.MONTH);
		int iYear=aYear;
		int iMonth;*/

        String nowMonth = DictKeys.monthDateFormat.format(calendar.getTime());
		/*iMonth=aMonth+1;
		if(iMonth==13){
			iYear=aYear+1;
			iMonth=1;
		}
		calendar.set(iYear, iMonth, 1);
		String nextMonth=DictKeys.monthDateFormat.format(calendar.getTime());
		iMonth=aMonth-1;
		if(iMonth==0){
			iYear=aYear-1;
			iMonth=12;
		}
		calendar.set(iYear, iMonth, 1);*/
        calendar.add(calendar.MONTH, 1);
        String nextMonth = DictKeys.monthDateFormat.format(calendar.getTime());
        calendar.add(calendar.MONTH, -2);
        String preMonth = DictKeys.monthDateFormat.format(calendar.getTime());
        logger.debug("this is:" + "now--" + nowMonth + "\tnextMonth" + nextMonth + "\tpreMonth" + preMonth);
        int nowCount = NewsService.getInstance().countTitles(nowMonth);
        int preCount = NewsService.getInstance().countTitles(preMonth);
        int nextCount = NewsService.getInstance().countTitles(nextMonth);
        setSessionAttr("preCount", preCount);
        setSessionAttr("nowCount", nowCount);
        setSessionAttr("nextCount", nextCount);
        setSessionAttr("nowMonth", nowMonth);
        setSessionAttr("urlState", action);
        setSessionAttr("nextMonth", nextMonth);
        setSessionAttr("preMonth", preMonth);
        setAttr("urlState", action);
        render("/platform/index.html#indexContent");
    }


    public void listTitle() {
        String target = getPara(0);
        List<Title> titles = null;
        if (target != null) {
            titles = NewsService.getInstance().viewtitles(target);
        }
        if (titles == null) {
            titles = new ArrayList<Title>();
            Title title = new Title("", "404! cannot find!", "无", "无",
                    "无", "无", null,
                    null, 0, "无");
            titles.add(title);
        }
        renderJson("list", titles);
    }

    public void listArticle() {

    }

    public void articleAdd() {
        String mHttpUrl = SystemTools.getWebRootPath() + "\\upload\\logo";
        int maxSize = 10 * 1024 * 1024;              //10M
        UploadFile uploadFile = getFile("file", mHttpUrl, maxSize, "utf-8");
        File file = null;
        Title title = new Title();
        Article article = new Article();
        this.setArticle(article);
        this.setTitle(title);
        File temp = null;
        if (uploadFile != null) {
            String[] extension = uploadFile.getFileName().split("\\.");
            String originalFilename = uploadFile.getOriginalFileName();
            file = uploadFile.getFile();
            String id = title.getId();
            temp = new File(mHttpUrl + "\\" + id + "." + extension[extension.length - 1]);
            if (temp.isFile()) {
                temp.delete();
            }
            System.out.println(temp + "\n####\n" + file);
            file.renameTo(temp);
        }
        article.setId(title.getId());
        article.setDescription(title.getDescription());
        if (file != null) {
            title.setLogo("/upload/logo/" + temp.getName());
        } else
            title.setLogo("/img/default/" + Integer.parseInt(title.getLogo()) % 40
                    + ".jpg");
        NewsService.getInstance().saveNews(title, article);
        setAttr("urlState", "news-add");
        render("/platform/index.html");
    }

    public void save() {
        Title title = new Title();
        Article article = new Article();
        this.setTitle(title);
        this.setArticle(article);
        NewsService.getInstance().saveNews(title, article);
        setAttr("urlState", "news-add");
        render("/platform/index.html");
    }

    @ActionKey("/news/type/add")
    public void addType() {
        Type type = new Type();
        this.setType(type);
        NewsService.getInstance().saveType(type);
        setAttr("urlState", "news-type-add");
        render("/platform/index.html");
    }

    @ActionKey("/news/type/list")
    public void listType() {

        List<Type> types = NewsService.getInstance().getAllType();
        renderJson("list", types);
    }

    @ActionKey("/news/title/delete")
    public void deleteTitle() {
        String target = getPara(0);
        String id = getPara(1);
        String is = getPara(2);
        if (is == null)
            is = "yes";

        NewsService.getInstance().deleteTitleByDir(is, "\\Title", target, id);
        setAttr("urlState", "news-list");
        render("/platform/index.html#indexContent");
    }

    @ActionKey("/news/type/delete")
    public void deleteType() {
        String id = getPara(0);

        NewsService.getInstance().deleteType(id);
        setAttr("urlState", "news-type-list");
        render("/platform/index.html#indexContent");
    }

    @ActionKey("/news/type/title/delete")
    public void deleteTypeTitle() {
        String target = getPara(0);
        String id = getPara(1);
        String is = getPara(2);
        if (is == null)
            is = "no";

        NewsService.getInstance().deleteTitleByDir(is, "\\Type", target, id);
        setAttr("urlState", "news-config");
        render("/platform/index.html#indexContent");
    }

    @ActionKey("/news/type/preModify")
    public void preModifyType() {
        String id = getPara(0);

        Type type = NewsService.getInstance().findTypeById(id);
        setAttr("entity", type);
        // render("/test/page.html#modalContent");
        render("/platform/news/type_modify.html");
    }

    @ActionKey("/news/type/rearModify")
    public void rearModifyType() {
        String id = getPara(0);

        Type type = NewsService.getInstance().findTypeById(id);
        setAttr("entity", type);
        // render("/test/page.html#modalContent");
        render("/platform/news/type_edit.html");

    }

    @ActionKey("/news/type/modify")
    public void modifyType() {
        String id = getPara("id");
        if (null != id) {
            Type type = new Type();
            this.setType(type);
            NewsService.getInstance().updateType(type);
            setAttr("urlState", "news-type-list");
            render("/platform/index.html");
        } else {
            render("/common/401.html");
        }

    }

    @ActionKey("/news/type/edit")
    public void editorType() {
        String id = getPara("id");
        String name = getPara("name");
        if (null != name) {
            NewsService.getInstance().updateTypeByName(id, name);
            setAttr("urlState", "news-type-index");
            render("/platform/index.html");
        } else {
            render("/common/401.html");
        }
    }

    private void setTitle(Title title) {
        title.setId(getPara("id", Calendar.getInstance().getTime()));
        title.setName(getPara("name"));
        title.setOutline(getPara("outline", "").trim());
        //	System.out.println(new Date()+""+getPara("outline")+"#######"+title.getOutline());
        // to get the date format of yyyyMM

        title.setLinkId(getParaForMonthDate("linkId", Calendar.getInstance()
                .getTime()));
        //System.out.println(new Date()+"####:"+title.getLinkId());
        title.setLogo(getPara("logo"));
        title.setCreatorId(getPara("creatorId"));
        title.setCreatorName(getPara("creatorName"));
        title.setCreatDate(getParaToDate("creatDate"));
        title.setEditDate(getParaToDate("editDate", Calendar.getInstance()
                .getTime()));
        title.setClickCount(getParaToInt("clickCount", 0));
        title.setDescription(getPara("description"));
    }

    private void setArticle(Article article) {
        article.setContent(this.getRequest().getParameter("content"));
    }

    private void setType(Type type) {
        type.setId(getPara("id", Calendar.getInstance().getTime()));
        type.setName(getPara("name"));
        type.setValue(getPara("value"));
        type.setDescription(getPara("description", ""));
    }

    @ActionKey("/news/type/ajaxCheck")
    public void ajaxCheck() {
        String valueName = getPara("fieldId");
        String value = getPara("fieldValue");
        renderJson("[\"" + valueName + "\","
                + NewsService.getInstance().checkValue(valueName, value) + "]");
    }

}
