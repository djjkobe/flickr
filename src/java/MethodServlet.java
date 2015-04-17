/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import Model.PhotoBean;
import javax.servlet.annotation.WebServlet;
import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.photos.Photo;
import com.flickr4java.flickr.photos.PhotoList;
import com.flickr4java.flickr.photos.PhotosInterface;
import com.flickr4java.flickr.photos.SearchParameters;
import com.flickr4java.flickr.tags.Tag;
import com.flickr4java.flickr.tags.TagsInterface;
import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Yeqiu
 */
@WebServlet(urlPatterns = {"/methodservlet"})
public class MethodServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
     String methodName;
    String keyWords;
    String language;
    String otherTranslation;
    int pageNum;
    int totalPage;
    Flickr flickr;
    String searchText;
    String title;
    Language lan;
    String origPage;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        // Start
        methodName = request.getParameter("methodName");
        
        
        if(methodName.equals("search")){
            if(request.getParameter("keyWords") == ""){
                request.getRequestDispatcher("/index.html").forward(request,response);
            }
            else{
            keyWords = request.getParameter("keyWords");
            language = request.getParameter("language");
            pageNum = 1;
            // Translate an english string to spanish
            
            switch(language){
                case "CHINESE_SIMPLIFIED":
                    lan = Language.CHINESE_SIMPLIFIED;
                    break;
                case "CHINESE_TRADITIONAL":
                    lan = Language.CHINESE_TRADITIONAL;
                    break;
                case "FRENCH":
                    lan = Language.FRENCH;
                    break;
                case "DUTCH":
                    lan = Language.DUTCH;
                    break;
                case "RUSSIAN":
                    lan = Language.RUSSIAN;
                    break;
                case "SPANISH":
                    lan = Language.SPANISH;
                    break;
                case "ITALIAN":
                    lan = Language.ITALIAN;
                    break;
            }
            
          //  splitedTags = splitedTags(englishString);
            searchText  = Translate.execute(keyWords,Language.ENGLISH);
            String[] constant = {"keyword", "original page"};
            constant = Translate.execute(constant,Language.ENGLISH, lan);
            title = constant[0];
            origPage = constant[1];
     
	    SearchParameters searchParams = new SearchParameters();
	    searchParams.setSort(SearchParameters.RELEVANCE);
	   
	    //Create tag keyword array
             
            
	    searchParams.setText(searchText);

	    //Initialize PhotosInterface object
	    PhotosInterface photosInterface = flickr.getPhotosInterface();
	    
	    //Execute search with entered tags
	    PhotoList photoList = photosInterface.search(searchParams, 50, pageNum);
            totalPage = photoList.getPages();

            // PhotoList
            List<PhotoBean> albums = new ArrayList<>();
            
	    //get search result and fetch the photo object and get small square imag's url
	    if(photoList != null){
                //Get search result and check the size of photo result
                for (Object photoList1 : photoList) {
                    //get photo object
                    Photo photo = (Photo) photoList1;
                    PhotoBean photoBean = new PhotoBean();
                    photoBean.setPhotoID(photo.getId());
                    photoBean.setPhotoSmallURL(getImageUrl(photo));  
                    photoBean.setPhotoBigURL(getImageUrl(photo));
                    photoBean.setPhotoURL(photo.getUrl());
                    photoBean.setPhotoSecret(photo.getSecret());
                    albums.add(photoBean);
                }
	    }
            
            // set parameters
            request.setAttribute("photos", albums);
            request.setAttribute("totalPage",totalPage);
            request.setAttribute("keyWords",keyWords);
            request.setAttribute("language",language);
            request.getRequestDispatcher("/showImages.jsp").forward(request,response);
            }
        }
        
             if(methodName.equals("more")){
                 pageNum = Integer.parseInt(request.getParameter("cuPage"));
                 pageNum++;
            //Get photo
            //Set API key

	    //initialize SearchParameter object, this object stores the search keyword
	    SearchParameters searchParams = new SearchParameters();
	    searchParams.setSort(SearchParameters.RELEVANCE);
	   
	    //Create tag keyword array
	    searchParams.setText(searchText);

	    //Initialize PhotosInterface object
	    PhotosInterface photosInterface = flickr.getPhotosInterface();
	    //Execute search with entered tags
	    PhotoList photoList = photosInterface.search(searchParams, 50, pageNum);

            // PhotoList
            List<PhotoBean> albums = new ArrayList<>();
            
	    //get search result and fetch the photo object and get small square imag's url
	    if(photoList != null){
                //Get search result and check the size of photo result
                for (Object photoList1 : photoList) {
                    //get photo object
                    Photo photo = (Photo) photoList1;
                    PhotoBean photoBean = new PhotoBean();
                    photoBean.setPhotoID(photo.getId());
                    photoBean.setPhotoSmallURL(getImageUrl(photo));
                    photoBean.setPhotoBigURL(getImageUrl(photo));
                    photoBean.setPhotoURL(photo.getUrl());
                    photoBean.setPhotoSecret(photo.getSecret());
                    photoBean.setTitle(title);
                    albums.add(photoBean);
                }
	    }
            
                    response.setContentType("text/html;charset=UTF-8");
             PrintWriter out = response.getWriter();
            /* TODO output your page here. You may use following sample code. */
            out.write("<content>");
            for(int i = 0; i< albums.size(); i++){
                PhotoBean cache = albums.get(i);       
                String data = "<photo photoId='"+cache.getPhotoID()+"' photoSmallURL='"+cache.getPhotoSmallURL()+"' photoBigURL='"+cache.getPhotoBigURL()+"' photoURL='"+cache.getPhotoURL()+"' photoSecret='"+cache.getPhotoSecret()+"' />";
                out.write(data);
                
            }
            out.write("</content>");
            out.flush();
            out.close();
            
        }
             
             if(methodName.equals("detail")){
                 String photoId = request.getParameter("photoId");
                 String photoSecret = request.getParameter("photoSecret");
                 
                  PhotosInterface photosInterface = flickr.getPhotosInterface();
                   Photo temp = (Photo)photosInterface.getInfo(photoId, photoSecret);
                   /*
                 TagsInterface tagsInterface = flickr.getTagsInterface();
                 Photo temp= tagsInterface.getListPhoto(photoId); */
                   
                 PhotoBean photoBean = new PhotoBean();
                 photoBean.setTitle(title);
                  
                 String imTitle;
                    if(temp.getTitle() == null)
                     imTitle = "No Title!";
                    else
                        imTitle = temp.getTitle().replaceAll("\"|\n|\t|\r|\'","");
                     
                 
                 String description;
                 if(temp.getDescription() == null)
                    description = "No Description!";
                 else
                     description = temp.getDescription().replaceAll("\"|\n|\t|\r|\'","");
   
                 
                     ArrayList<Tag> tagsCache = (ArrayList<Tag>)temp.getTags();
                    String[] tagCot = new String[tagsCache.size()+2];
                    tagCot[0] = imTitle;
                    tagCot[1] = description;
                    for(int i =2; i<tagCot.length; i++){
                        tagCot[i] = tagsCache.get(i-2).getRaw();                      
                    }
                   String[]  tagCotTran = new String[tagCot.length];
                   for(int i = 0; i< tagCot.length; i++){
                       tagCotTran[i] = tagCot[i];
                   }
                      tagCotTran = Translate.execute(tagCotTran,Language.ENGLISH ,lan);                   
                    
                    response.setContentType("text/html;charset=UTF-8");
             PrintWriter out = response.getWriter();
             out.write("<content>");
             String tagsLine = "";
                for(int j = 2; j< tagCotTran.length; j++){
                    if(j == tagCotTran.length-1){
                        tagsLine = tagsLine + tagCotTran[j];
                        continue;
                    }
                    tagsLine = tagsLine + tagCotTran[j] + "|?|";
                }
               
                String origTagsLine = "";
               for(int j = 2; j< tagCot.length; j++){
                    if(j == tagCot.length-1){
                        origTagsLine = origTagsLine + tagCot[j];
                        continue;
                    }
                    origTagsLine = origTagsLine + tagCot[j] + "|?|";
                }             

                String data = "<photo keyWordTitle='"+photoBean.getTitle()+"' tagsLine='"+tagsLine+"' photoURL='"+request.getParameter("photoURL")+"' photoTitle='"+tagCotTran[0]+"' photoDescription='"+tagCotTran[1]+"' origKeyWordTitle='Keyword' origPhotoTitle='"+tagCot[0]+"' origPhotoDescription='"+tagCot[1]+"' origTagsLine='"+origTagsLine+"' origPage='Original Page' page='"+origPage+"' />";
                out.write(data);
                out.write("</content>");
            out.flush();
            out.close();
             }
      
    }
    
     public String getImageUrl(Photo photo) {
        StringBuilder res = new StringBuilder();
        res.append("https://farm");
        res.append(photo.getFarm());
        res.append(".static.flickr.com/");
        res.append(photo.getServer());
        res.append("/");
        res.append(photo.getId());
        res.append("_");
        res.append(photo.getSecret());
        res.append("_z.");
        res.append(photo.getOriginalFormat());
        return res.toString();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
         try {
             processRequest(request, response);
         } catch (Exception ex) {
             Logger.getLogger(MethodServlet.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         try {
             processRequest(request, response);
         } catch (Exception ex) {
             Logger.getLogger(MethodServlet.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    @Override
    public void init() throws ServletException {
        
        Translate.setClientId("informationstorage001");
            Translate.setClientSecret("/9+ln1+Td5B3vV/NMpfG5cEuK/7FOR5G70bJBCx/8Ss=");
            
            String key = "ea0b8716e82373a0023a4090fcfaea22";
	    String secret = "c7446fdc66bf69e6";
	   
	    //initialize Flickr object with key
	    flickr=new Flickr(key, secret, new REST());
            super.init();
//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void destroy() {
        super.destroy(); //To change body of generated methods, choose Tools | Templates.
    }
   

}



