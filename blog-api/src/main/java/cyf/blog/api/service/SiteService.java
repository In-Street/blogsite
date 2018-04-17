package cyf.blog.api.service;

import cyf.blog.base.common.Constants;
import cyf.blog.base.enums.db.ContentStatus;
import cyf.blog.base.enums.db.ContentType;
import cyf.blog.base.enums.db.MetaType;
import cyf.blog.dao.mapper.AttachMapper;
import cyf.blog.dao.mapper.ContentsMapper;
import cyf.blog.dao.mapper.MetasMapper;
import cyf.blog.dao.model.AttachExample;
import cyf.blog.dao.model.Contents;
import cyf.blog.dao.model.ContentsExample;
import cyf.blog.dao.model.Metas;
import cyf.blog.dao.model.MetasExample;
import cyf.blog.dao.model.bo.ArchiveBo;
import cyf.blog.dao.model.bo.BackResponseBo;
import cyf.blog.dao.model.bo.StatisticsBo;
import cyf.blog.util.DateKit;
import cyf.blog.util.TextUtil;
import cyf.blog.util.ZipUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * 站点服务：归档
 *
 * @author Cheng Yufei
 * @create 2018-03-11 下午3:58
 **/
@Service
@Slf4j
public class SiteService {

    @Autowired
    private ContentsMapper contentsMapper;
    @Autowired
    private MetasMapper metasMapper;
    @Autowired
    private AttachMapper attachMapper;

    /**
     * 归档
     * @return
     */
    public List<ArchiveBo> getArchives() {
        //根据时间group，按x年x月 分组
        List<ArchiveBo> archiveBos = contentsMapper.getArchiveBos();
        if (CollectionUtils.isEmpty(archiveBos)) {
            return archiveBos;
        }
        ContentsExample example = new ContentsExample();
        //根据分组时间找文章
        archiveBos.forEach(a->{
            example.setOrderByClause("created desc,cid desc");
            String date = a.getDate();
            Date dateParse = null;
            try {
                 dateParse = FastDateFormat.getInstance("yyyy-MM").parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            example.createCriteria().andCreatedGreaterThanOrEqualTo(dateParse).andCreatedLessThan(DateUtils.addMonths(dateParse, 1))
                    .andTypeEqualTo(ContentType.post.getCode()).andStatusEqualTo(ContentStatus.publish.getCode());
            List<Contents> contents = contentsMapper.selectByExample(example);
            a.setArticles(contents);
            example.clear();
        });

        return archiveBos;
    }

    public List<Metas> getLinks() {
        MetasExample example = new MetasExample();
        example.setOrderByClause("sort asc");
        example.createCriteria().andTypeEqualTo(MetaType.link.getCode());
        List<Metas> metas = metasMapper.selectByExample(example);
        return metas;
    }

    public StatisticsBo getStatistics() {

        ContentsExample contentExample = new ContentsExample();
        contentExample.createCriteria().andStatusEqualTo(ContentStatus.publish.getCode()).andTypeEqualTo(ContentType.post.getCode());
        long postCount = contentsMapper.countByExample(contentExample);

        MetasExample metasExample = new MetasExample();
        metasExample.createCriteria().andTypeEqualTo(MetaType.link.getCode());
        long linkCount = metasMapper.countByExample(metasExample);

        long attachCount = attachMapper.countByExample(new AttachExample());
        StatisticsBo statistics = new StatisticsBo();
        statistics.setArticles(postCount);
//        statistics.setComments(comments);
        statistics.setAttachs(attachCount);
        statistics.setLinks(linkCount);

        return statistics;
    }


    public BackResponseBo backup(String bk_type, String bk_path, String fmt) throws Exception {
        BackResponseBo backResponse = new BackResponseBo();
        if (bk_type.equals("attach")) {
            if (StringUtils.isBlank(bk_path)) {
                throw new Exception("请输入备份文件存储路径");
            }
            if (!(new File(bk_path)).isDirectory()) {
                throw new Exception("请输入一个存在的目录");
            }

            String bkAttachDir = Constants.CLASSPATH + "upload";
            String bkThemesDir = Constants.CLASSPATH + "templates/themes";

            String fname = DateKit.dateFormat(new Date(), fmt) + "_" + TextUtil.getRandomNumber(5) + ".zip";

            String attachPath = bk_path + "/" + "attachs_" + fname;
            String themesPath = bk_path + "/" + "themes_" + fname;

            ZipUtils.zipFolder(bkAttachDir, attachPath);
            ZipUtils.zipFolder(bkThemesDir, themesPath);

            backResponse.setAttachPath(attachPath);
            backResponse.setThemePath(themesPath);
        }
       /* if (bk_type.equals("db")) {

            String bkAttachDir = AttachController.CLASSPATH + "upload/";
            if (!(new File(bkAttachDir)).isDirectory()) {
                File file = new File(bkAttachDir);
                if (!file.exists()) {
                    file.mkdirs();
                }
            }
            String sqlFileName = "tale_" + DateKit.dateFormat(new Date(), fmt) + "_" + TaleUtils.getRandomNumber(5) + ".sql";
            String zipFile = sqlFileName.replace(".sql", ".zip");

            Backup backup = new Backup(TaleUtils.getNewDataSource().getConnection());
            String sqlContent = backup.execute();

            File sqlFile = new File(bkAttachDir + sqlFileName);
            write(sqlContent, sqlFile, Charset.forName("UTF-8"));

            String zip = bkAttachDir + zipFile;
            ZipUtils.zipFile(sqlFile.getPath(), zip);

            if (!sqlFile.exists()) {
                throw new TipException("数据库备份失败");
            }
            sqlFile.delete();

            backResponse.setSqlPath(zipFile);

            // 10秒后删除备份文件
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    new File(zip).delete();
                }
            }, 10 * 1000);
        }*/
        return backResponse;
    }

}
