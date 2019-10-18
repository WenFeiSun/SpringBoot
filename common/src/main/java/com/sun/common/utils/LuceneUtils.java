package com.sun.common.utils;

import com.sun.common.entity.User;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class LuceneUtils {

    private static Directory directory;

    private static Analyzer analyzer;

    private static IndexWriterConfig config;

    private static IndexWriter writer;

    private static IndexReader reader;

    public void IndexCrud() {
        try {
            //本地存储
            directory = FSDirectory.open(Paths.get("D:\\luence"));
            //内存存储
            //directory = new RAMDirectory();
            //初始化分词器
            analyzer = new StandardAnalyzer();
            //config
            config = new IndexWriterConfig(analyzer);
            //index writer
            writer = new IndexWriter(directory, config);

            //index reader
            reader = DirectoryReader.open(directory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建索引
     */
    public void createIndex(List<User> indexList){

        try {
            //创建writer
            List<Document> docs = new ArrayList<Document>();
            for(int i = 0; i < indexList.size(); i++){
                Document doc = new Document();
                doc.add(new StringField(indexList.get(i).getUserId(), indexList.get(i).getUserId(), Field.Store.YES));
                doc.add(new StringField(indexList.get(i).getUserName(), indexList.get(i).getUserName(), Field.Store.YES));
                Field field = new TextField(indexList.get(i).getPwd(), indexList.get(i).getPwd(), Field.Store.YES);
                doc.add(field);

                //加权操作。qq邮箱2.0  新浪有限1.5  其他默认1.0 谷歌0.5
                //1.权值越高，查询结果越靠前。
                //2.lucene4.0以后不能对doc加权
                //3.只能对TextField加权
                /*if(emails[i].indexOf("qq.com")!=-1){
                    field.setBoost(2.0f);
                }else if(emails[i].indexOf("sina.com")!=-1){
                    field.setBoost(1.5f);
                }else if(emails[i].indexOf("google")!=-1){
                    field.setBoost(0.5f);
                }
                doc.add(new IntField("fileSize", fileSizes[i], Field.Store.YES));
                //对于内容只索引不存储
                doc.add(new TextField("content", contents[i], Field.Store.NO));*/
                writer.addDocument(doc);
            }
            writer.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 索引更新
     */
    public void updateIndex(User user){
        try {
            Document doc = new Document();
            doc.add(new StringField("userId", user.getUserId(), Field.Store.YES));
            doc.add(new StringField("userName", user.getUserName(), Field.Store.YES));
            doc.add(new StringField("tel", user.getUserTel(), Field.Store.YES));
            /*doc.add(new IntField("fileSize", fileSizes[1], Field.Store.YES));
            doc.add(new TextField("content", contents[1], Field.Store.NO));*/

            //更新的时候，会把原来那个索引删掉，重新生成一个索引
            Term term = new Term("userId", user.getUserId());
            writer.updateDocument(term, doc);
            writer.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 索引全部删除
     */
    public void deleteAllIndex(){
        try {
            writer.deleteAll();
            writer.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 索引删除
     */
    public void deleteIndex(){
        try {
            Term[] terms = new Term[2];
            Term term = new Term("id", "1");
            terms[0] = term;
            term = new Term("id", "3");
            terms[1] = term;
            //将id为 1和3的索引删除。
            //也可以传一个Query数组对象，将Query查找的结果删除。
            writer.deleteDocuments(terms);
            //deleteDocuments
            writer.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static IndexSearcher getSearcher(){
        try {
            IndexReader newReader = DirectoryReader.openIfChanged((DirectoryReader)reader);
            if(newReader != null){
                reader.close();
                reader = newReader;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new IndexSearcher(reader);
    }

    /**
     * 读取索引
     */
    public void readIndex(){
        System.out.println("max num:"+reader.maxDoc());
        System.out.println("index num:"+reader.numDocs());
        //删除了的索引数
        System.out.println("delete index num:"+reader.numDeletedDocs());
    }

    public void queryIndex(){
        ScoreDoc[] hits;
        try {
            //搜索器
            IndexSearcher searcher = getSearcher();
            //查询哪个字段
            QueryParser parse = new QueryParser("email", analyzer);
            //查询关键字
            Query query = parse.parse("zs");
            TopDocs topDocs = searcher.search(query, 1000);

            //碰撞结果
            hits = topDocs.scoreDocs;

            for (int i = 0; i < hits.length; i++) {
                ScoreDoc hit = hits[i];
                Document hitDoc = searcher.doc(hit.doc);
                //结果按照得分来排序。主要由 关键字的个数和权值来决定
                System.out.println("("+hit.doc+"-"+hit.score+")"+"id:"+hitDoc.get("id")+" name:"+hitDoc.get("name")+" email:"+hitDoc.get("email"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
