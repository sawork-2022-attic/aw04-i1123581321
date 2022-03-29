package com.example.webpos.model.repository;

import com.example.webpos.model.entity.Product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public class JDRepository implements ProductRepository {
    private List<Product> products = null;


    @Override
    public Optional<Product> findById(String s) {
        for (Product p: findAll()){
            if (p.getId().equals(s)){
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

    @Override
    @Cacheable("products")
    public Iterable<Product> findAll() {
        try {
            if (products == null)
                products = parseJD("Java");
        } catch (IOException e) {
            products = new ArrayList<>();
        }
        return products;
    }

    public static List<Product> parseJD(String keyword) throws IOException{
        String url = "https://search.jd.com/Search?keyword=" + keyword;
        String cookie = "paste your cookie here";
        HashMap<String, String> cookieMap = new HashMap<>();
        String[] items = cookie.trim().split(";");
        for (String item: items){
            String[] kv = item.trim().split("=");
            cookieMap.put(kv[0], kv[1]);
        }
        Document document = Jsoup.connect(url).cookies(cookieMap).get();
        //所有js的方法都能用
        Element element = document.getElementById("J_goodsList");
        //获取所有li标签
        Elements elements = element.getElementsByTag("li");
//        System.out.println(element.html());
        List<Product> list = new ArrayList<>();

        //获取元素的内容
        for (Element el : elements
        ) {
            //关于图片特别多的网站，所有图片都是延迟加载的
            String id = el.attr("data-spu");
            String img = "https:".concat(el.getElementsByTag("img").eq(0).attr("data-lazy-img"));
            String price = el.getElementsByAttribute("data-price").text();
            String title = el.getElementsByClass("p-name").eq(0).text();
            if (title.contains("，"))
                title = title.substring(0, title.indexOf("，"));

            Product product = new Product(id, title, BigDecimal.valueOf(Double.parseDouble(price)), img);

            list.add(product);
        }
        return list;
    }
}
