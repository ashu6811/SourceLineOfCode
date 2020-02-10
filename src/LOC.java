import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

public class LOC {
    // All the static Variables
    public static ArrayList<File> allJavaFiles = new ArrayList<>();
    public static ArrayList<File> uniqueJavaFiles = new ArrayList<>();
    public static Integer numBlankLines = 0;
    public static Integer numCodeLines = 0;
    public static Integer numCommentLines = 0;
    public static Integer numTotalLines = 0;
    //MAIN Function
    public static void main(String[] args) {
        String path = "/Users/ashmeetsingh/Downloads/commons-io-master";
        System.out.print(calcNumOfFiles(path) + " - ");
        System.out.print(calcNumOfUniqueFiles()+ " - ");
        calcLines();
        System.out.println(numBlankLines + " - " + numCommentLines + " - " + numCodeLines);




    }

    //All the static Functions
    public static boolean isJava(File f)
    {
        String fName = f.getName();
        String subname = fName.substring(fName.indexOf('.'));
        if (subname.equals(".java"))
            return true;

        return false;
    }
    public static Integer calcNumOfFiles (String path)
    {
        File file = new File(path);

        try {
            int count = 0;
            for (File f : file.listFiles()) {
                if (f.isFile() && isJava(f)) {
                    count++;
                    allJavaFiles.add(f);
                }
                if (f.isDirectory()) {
                    count += calcNumOfFiles(f.getPath());
                }
            }
            return count;
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }

    return null;
    }

    public static Integer calcNumOfUniqueFiles ()
    {
        try
        {
            for (int i=0; i<allJavaFiles.size()-1; i++)
            {
                for(int j=i+1; j<allJavaFiles.size(); j++)
                {
                    if(compareFiles(allJavaFiles.get(i),allJavaFiles.get(j)))
                    {
                        allJavaFiles.remove(j);
                    }
                }
            }
            return allJavaFiles.size();
            //System.out.println(compareFiles(allJavaFiles.get(222), allJavaFiles.get(222)));
            //System.out.println(compareFiles(allJavaFiles.get(1), allJavaFiles.get(0)));

        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }

        return allJavaFiles.size();
    }
    public static boolean compareFiles(File f1, File f2)
    {
        try{
            BufferedReader fb1 = new BufferedReader(new FileReader(f1));
            BufferedReader fb2 = new BufferedReader(new FileReader(f2));
            if(f1.length()==f2.length())
            {
                String tempLine1, tempLine2;
                while(!(tempLine1 = fb1.readLine()).isEmpty())
                {
                    if (!(tempLine2 = fb2.readLine()).isEmpty())
                    {
                        if(!tempLine1.equals(tempLine2))
                        {
                            return false;
                        }
                    }
                    else return false;
                }
            }
            else return false;
            return true;
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }

       return false;
    }

    public static Integer calcLines()
    {
        for(File f: allJavaFiles)
        { try
            {
                BufferedReader fb = new BufferedReader(new FileReader(f));
                String tempLine="";
                boolean flag = false;
                while(fb!=null && (tempLine=fb.readLine())!=null)
                {
                    numTotalLines++;
                    if(tempLine.trim().startsWith("/*") && flag == false)
                    {
                        flag =true;
                    }
                    if(flag)
                    {
                        numCommentLines++;
                    }
                    if(flag ==false)
                    {
                        if(tempLine.isBlank())
                            numBlankLines++;
                        if(tempLine.trim().startsWith("//"))
                            numCommentLines++;
                        if(!tempLine.isBlank() && !tempLine.trim().startsWith("//"))
                            numCodeLines++;
                    }
                    if(tempLine.contains("*/") && flag == true)
                    {
                        flag = false;
                    }


                }
                fb.close();
            }
            catch (Exception ex)
            {
                //System.out.println(f.getName() + ex);
            }
        }
        return numBlankLines;
    }


}
