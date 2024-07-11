
package com.controller;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import com.alibaba.fastjson.JSONObject;
import java.util.*;
import org.springframework.beans.BeanUtils;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.ContextLoader;
import javax.servlet.ServletContext;
import com.service.TokenService;
import com.utils.*;
import java.lang.reflect.InvocationTargetException;

import com.service.DictionaryService;
import org.apache.commons.lang3.StringUtils;
import com.annotation.IgnoreAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.entity.*;
import com.entity.view.*;
import com.service.*;
import com.utils.PageUtils;
import com.utils.R;
import com.alibaba.fastjson.*;

/**
 * 汽车
 * 后端接口
 * @author
 * @email
*/
@RestController
@Controller
@RequestMapping("/cheliang")
public class CheliangController {
    private static final Logger logger = LoggerFactory.getLogger(CheliangController.class);

    @Autowired
    private CheliangService cheliangService;


    @Autowired
    private TokenService tokenService;
    @Autowired
    private DictionaryService dictionaryService;

    //级联表service
    @Autowired
    private ShangjiaService shangjiaService;

    @Autowired
    private YonghuService yonghuService;


    /**
    * 后端列表
    */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("page方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));
        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(false)
            return R.error(511,"永不会进入");
        else if("用户".equals(role))
            params.put("yonghuId",request.getSession().getAttribute("userId"));
        else if("商家".equals(role))
            params.put("shangjiaId",request.getSession().getAttribute("userId"));
        params.put("cheliangDeleteStart",1);params.put("cheliangDeleteEnd",1);
        if(params.get("orderBy")==null || params.get("orderBy")==""){
            params.put("orderBy","id");
        }
        PageUtils page = cheliangService.queryPage(params);

        //字典表数据转换
        List<CheliangView> list =(List<CheliangView>)page.getList();
        for(CheliangView c:list){
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(c, request);
        }
        return R.ok().put("data", page);
    }

    /**
    * 后端详情
    */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id, HttpServletRequest request){
        logger.debug("info方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        CheliangEntity cheliang = cheliangService.selectById(id);
        if(cheliang !=null){
            //entity转view
            CheliangView view = new CheliangView();
            BeanUtils.copyProperties( cheliang , view );//把实体数据重构到view中

                //级联表
                ShangjiaEntity shangjia = shangjiaService.selectById(cheliang.getShangjiaId());
                if(shangjia != null){
                    BeanUtils.copyProperties( shangjia , view ,new String[]{ "id", "createTime", "insertTime", "updateTime"});//把级联的数据添加到view中,并排除id和创建时间字段
                    view.setShangjiaId(shangjia.getId());
                }
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(view, request);
            return R.ok().put("data", view);
        }else {
            return R.error(511,"查不到数据");
        }

    }

    /**
    * 后端保存
    */
    @RequestMapping("/save")
    public R save(@RequestBody CheliangEntity cheliang, HttpServletRequest request){
        logger.debug("save方法:,,Controller:{},,cheliang:{}",this.getClass().getName(),cheliang.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(false)
            return R.error(511,"永远不会进入");
        else if("商家".equals(role))
            cheliang.setShangjiaId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));

        Wrapper<CheliangEntity> queryWrapper = new EntityWrapper<CheliangEntity>()
            .eq("shangjia_id", cheliang.getShangjiaId())
            .eq("cheliang_name", cheliang.getCheliangName())
            .eq("cheliang_types", cheliang.getCheliangTypes())
            .eq("cheliang_clicknum", cheliang.getCheliangClicknum())
            .eq("zan_number", cheliang.getZanNumber())
            .eq("cai_number", cheliang.getCaiNumber())
            .eq("shangxia_types", cheliang.getShangxiaTypes())
            .eq("cheliang_delete", cheliang.getCheliangDelete())
            ;

        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        CheliangEntity cheliangEntity = cheliangService.selectOne(queryWrapper);
        if(cheliangEntity==null){
            cheliang.setCheliangClicknum(1);
            cheliang.setShangxiaTypes(1);
            cheliang.setCheliangDelete(1);
            cheliang.setCreateTime(new Date());
            cheliangService.insert(cheliang);
            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }

    /**
    * 后端修改
    */
    @RequestMapping("/update")
    public R update(@RequestBody CheliangEntity cheliang, HttpServletRequest request){
        logger.debug("update方法:,,Controller:{},,cheliang:{}",this.getClass().getName(),cheliang.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
//        if(false)
//            return R.error(511,"永远不会进入");
//        else if("商家".equals(role))
//            cheliang.setShangjiaId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));
        //根据字段查询是否有相同数据
        Wrapper<CheliangEntity> queryWrapper = new EntityWrapper<CheliangEntity>()
            .notIn("id",cheliang.getId())
            .andNew()
            .eq("shangjia_id", cheliang.getShangjiaId())
            .eq("cheliang_name", cheliang.getCheliangName())
            .eq("cheliang_types", cheliang.getCheliangTypes())
            .eq("cheliang_clicknum", cheliang.getCheliangClicknum())
            .eq("zan_number", cheliang.getZanNumber())
            .eq("cai_number", cheliang.getCaiNumber())
            .eq("shangxia_types", cheliang.getShangxiaTypes())
            .eq("cheliang_delete", cheliang.getCheliangDelete())
            ;

        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        CheliangEntity cheliangEntity = cheliangService.selectOne(queryWrapper);
        if("".equals(cheliang.getCheliangPhoto()) || "null".equals(cheliang.getCheliangPhoto())){
                cheliang.setCheliangPhoto(null);
        }
        if(cheliangEntity==null){
            cheliangService.updateById(cheliang);//根据id更新
            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }



    /**
    * 删除
    */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
        logger.debug("delete:,,Controller:{},,ids:{}",this.getClass().getName(),ids.toString());
        ArrayList<CheliangEntity> list = new ArrayList<>();
        for(Integer id:ids){
            CheliangEntity cheliangEntity = new CheliangEntity();
            cheliangEntity.setId(id);
            cheliangEntity.setCheliangDelete(2);
            list.add(cheliangEntity);
        }
        if(list != null && list.size() >0){
            cheliangService.updateBatchById(list);
        }
        return R.ok();
    }


    /**
     * 批量上传
     */
    @RequestMapping("/batchInsert")
    public R save( String fileName, HttpServletRequest request){
        logger.debug("batchInsert方法:,,Controller:{},,fileName:{}",this.getClass().getName(),fileName);
        Integer yonghuId = Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId")));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            List<CheliangEntity> cheliangList = new ArrayList<>();//上传的东西
            Map<String, List<String>> seachFields= new HashMap<>();//要查询的字段
            Date date = new Date();
            int lastIndexOf = fileName.lastIndexOf(".");
            if(lastIndexOf == -1){
                return R.error(511,"该文件没有后缀");
            }else{
                String suffix = fileName.substring(lastIndexOf);
                if(!".xls".equals(suffix)){
                    return R.error(511,"只支持后缀为xls的excel文件");
                }else{
                    URL resource = this.getClass().getClassLoader().getResource("static/upload/" + fileName);//获取文件路径
                    File file = new File(resource.getFile());
                    if(!file.exists()){
                        return R.error(511,"找不到上传文件，请联系管理员");
                    }else{
                        List<List<String>> dataList = PoiUtil.poiImport(file.getPath());//读取xls文件
                        dataList.remove(0);//删除第一行，因为第一行是提示
                        for(List<String> data:dataList){
                            //循环
                            CheliangEntity cheliangEntity = new CheliangEntity();
//                            cheliangEntity.setShangjiaId(Integer.valueOf(data.get(0)));   //商家 要改的
//                            cheliangEntity.setCheliangName(data.get(0));                    //汽车名称 要改的
//                            cheliangEntity.setCheliangPhoto("");//详情和图片
//                            cheliangEntity.setCheliangTypes(Integer.valueOf(data.get(0)));   //汽车品牌 要改的
//                            cheliangEntity.setCheliangNewMoney(data.get(0));                    //价格 要改的
//                            cheliangEntity.setCheliangClicknum(Integer.valueOf(data.get(0)));   //点击次数 要改的
//                            cheliangEntity.setZanNumber(Integer.valueOf(data.get(0)));   //赞 要改的
//                            cheliangEntity.setCaiNumber(Integer.valueOf(data.get(0)));   //踩 要改的
//                            cheliangEntity.setCheliangContent("");//详情和图片
//                            cheliangEntity.setShangxiaTypes(Integer.valueOf(data.get(0)));   //是否上架 要改的
//                            cheliangEntity.setCheliangDelete(1);//逻辑删除字段
//                            cheliangEntity.setCreateTime(date);//时间
                            cheliangList.add(cheliangEntity);


                            //把要查询是否重复的字段放入map中
                        }

                        //查询是否重复
                        cheliangService.insertBatch(cheliangList);
                        return R.ok();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return R.error(511,"批量插入数据异常，请联系管理员");
        }
    }





    /**
    * 前端列表
    */
    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("list方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));

        // 没有指定排序字段就默认id倒序
        if(StringUtil.isEmpty(String.valueOf(params.get("orderBy")))){
            params.put("orderBy","id");
        }
        if(StringUtils.isBlank(String.valueOf(params.get("cheliangNewMoneyStart")))){
            params.remove("cheliangNewMoneyStart");
        }
        if(StringUtils.isBlank(String.valueOf(params.get("cheliangNewMoneyEnd")))){
            params.remove("cheliangNewMoneyEnd");
        }
        PageUtils page = cheliangService.queryPage(params);

        //字典表数据转换
        List<CheliangView> list =(List<CheliangView>)page.getList();
        for(CheliangView c:list)
            dictionaryService.dictionaryConvert(c, request); //修改对应字典表字段
        return R.ok().put("data", page);
    }

    /**
    * 前端详情
    */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id, HttpServletRequest request){
        logger.debug("detail方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        CheliangEntity cheliang = cheliangService.selectById(id);
            if(cheliang !=null){

                //点击数量加1
                cheliang.setCheliangClicknum(cheliang.getCheliangClicknum()+1);
                cheliangService.updateById(cheliang);

                //entity转view
                CheliangView view = new CheliangView();
                BeanUtils.copyProperties( cheliang , view );//把实体数据重构到view中

                //级联表
                    ShangjiaEntity shangjia = shangjiaService.selectById(cheliang.getShangjiaId());
                if(shangjia != null){
                    BeanUtils.copyProperties( shangjia , view ,new String[]{ "id", "createDate"});//把级联的数据添加到view中,并排除id和创建时间字段
                    view.setShangjiaId(shangjia.getId());
                }
                //修改对应字典表字段
                dictionaryService.dictionaryConvert(view, request);
                return R.ok().put("data", view);
            }else {
                return R.error(511,"查不到数据");
            }
    }


    /**
    * 前端保存
    */
    @RequestMapping("/add")
    public R add(@RequestBody CheliangEntity cheliang, HttpServletRequest request){
        logger.debug("add方法:,,Controller:{},,cheliang:{}",this.getClass().getName(),cheliang.toString());
        Wrapper<CheliangEntity> queryWrapper = new EntityWrapper<CheliangEntity>()
            .eq("shangjia_id", cheliang.getShangjiaId())
            .eq("cheliang_name", cheliang.getCheliangName())
            .eq("cheliang_types", cheliang.getCheliangTypes())
            .eq("cheliang_clicknum", cheliang.getCheliangClicknum())
            .eq("zan_number", cheliang.getZanNumber())
            .eq("cai_number", cheliang.getCaiNumber())
            .eq("shangxia_types", cheliang.getShangxiaTypes())
            .eq("cheliang_delete", cheliang.getCheliangDelete())
            ;
        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        CheliangEntity cheliangEntity = cheliangService.selectOne(queryWrapper);
        if(cheliangEntity==null){
            cheliang.setCheliangDelete(1);
            cheliang.setCreateTime(new Date());
        cheliangService.insert(cheliang);
            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }


}
