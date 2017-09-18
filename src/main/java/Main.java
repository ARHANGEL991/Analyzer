

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main
{

    public static void main(String[] args) throws Exception
    {
        String pathfile = "E:\\Копии\\likesВлад.html";
        String exeption= "src\\res\\Uninclude.txt";
        List<String> lines = Files.readAllLines(Paths.get(pathfile), StandardCharsets.UTF_8);
        List<String> exeptions= Files.readAllLines(Paths.get(exeption));
        ArrayList<Word> top_words = new ArrayList<Word>();

        for (String line : lines)
        {

            line = RemoveAndLow(line);
            String[] words = (line.split("\\s"));

            for (String word : words) {
                if(!word.isEmpty() && !IsExeption(exeptions,word))
                if (!top_words.isEmpty())



                {
                    boolean inIt=false;
                    for (Word word_temp : top_words)
                    {
                        if (word.equals(new String(word_temp.getWord())))
                        {
                            word_temp.IncCount();
                            inIt=true;
                            break;
                        }

                    }
                    if(!inIt)
                    {
                        Word element = new Word(word);
                        top_words.add(element);
                        continue;
                    }
                }
                else
                    {
                    Word element = new Word(word);
                    top_words.add(element);
                }


            }
        }

        top_words.sort(new Comparator<Word>() {
            @Override
            public int compare(Word w1, Word w2) {
                if (w1.GetCount()<w2.GetCount())
                return 1;
                else return -1;
            }
        });



        for (Word word : top_words)
            System.out.println(word.getWord() + "-" + word.GetCount());
        System.out.println("Done");
    }
       static private  String RemoveAndLow(String inputString)
        {
            inputString= inputString.replaceAll("[0-9]|[.?!)(,:<>—«»=+]","");

            inputString= inputString.toLowerCase();
            return  inputString;

        }

        static private boolean IsExeption(List<String> expslines, String word)
        {
            for (String expline: expslines)
            {
                String[] exeptions=expline.split(",");

                for (String exeption: exeptions )
                {

                    if ((exeption.replace(" ","")).equals(word))
                        return true;
                }
            }
            return false;
        }

    }

