

import sun.dc.path.PathException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main
{
   public static Scanner in = new Scanner(System.in);


    public static void main(String[] args) throws Exception {





        boolean incorrect = true;
        while (incorrect)
            try {
                System.out.println("Введите полный путь к файлу");
                String pathfile = in.nextLine();


                int option= CheckInput("Введите 1 - для составления Топа слов \nВведите 2 - для проверки на правильность расставления скобок");
                switch (option) {
                    case 1: {
                        TopWords(pathfile);
                        incorrect = false;
                        break;

                    }
                    case 2: {
                        System.out.println( Check_brackets(pathfile)? "Correct":"Incorrect" );
                        incorrect = false;
                        break;
                    }

                }
                incorrect = false;
            }
            catch (PathException ex)
            {
                System.out.println("Введите корректный путь");
                incorrect = true;
            }

    }
       public static void TopWords(String pathfile) throws Exception //составляет Топ слов
       {
           String exeption = "src\\res\\Uninclude.txt";
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
                    boolean inIt=false;                     // было ли слово в топе
                    for (Word word_temp : top_words)
                    {
                        if (word.equals((word_temp.getWord()))) //если было
                        {
                            word_temp.IncCount();   //увеличиваем счётчик повторений
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
                else                //добавление первого слова в топ
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
        }); //сортировка по количеству повторений


           System.out.println("TOP Слов"); //вывод
        for (int counter=0;counter<10;counter++) {
            System.out.printf(counter+1+")");
            System.out.printf("%4s", (top_words.get(counter).getWord()));
            System.out.printf("%4s",  "-" + top_words.get(counter).GetCount()+" \n");
        }
        System.out.println("Done");
    }




       static private  String RemoveAndLow(String inputString) //удаляет не нужные символы в словах
        {
            inputString= inputString.replaceAll("[0-9]|[.?!)(,:<>—«»=+]","");

            inputString= inputString.toLowerCase();
            return  inputString;

        }

        static private boolean IsExeption(List<String> expslines, String word) //проверка является ли слово word исключением
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

        static private  boolean Check_brackets(String pathfile) throws Exception //функция проверки скобок
        {
            List<String> lines = Files.readAllLines(Paths.get(pathfile), StandardCharsets.UTF_8);
            char[][] bracketsopen=new char[][]{{'(','{','['},{')','}',']'}};

                Stack<Character> brackets=new Stack<>(); //Инициализация стека

            for(String line:lines)
            {

                for(int counter=0; counter<line.length();counter++)
                {


                    for (int i=0; i<2;i++)
                        for(int j=0; j<3;j++ )
                   if(bracketsopen[i][j]== (line.charAt(counter))) //если скобка
                    {
                        if(i==0)                                    //открывающаяся
                        {
                            brackets.push(bracketsopen[i][j]);
                            break;
                        }
                        else                                        //закрывающаяся
                        {
                            if(brackets.empty())                //скобка закрывается, а в стеке нет открывающейся
                                return  false;
                            else
                                {
                                    if(!(brackets.pop().equals(bracketsopen[0][j]))) //закр. скобка не того типа
                                        return false;
                                }



                        }



                    }
                }
            }
            if(brackets.empty()) //пуст,значит все скобки расставлены верно
            return true;
            else return false;



        }

        public static  int CheckInput(String message)
        {
        System.out.print(message);
        int number = 1;
        boolean correct = false;
        while (!correct) {
            try {
                number = in.nextInt();


            } catch (Exception ex) {

                System.out.println("Введите число 1 или 2 !");
                correct = false;
            }
            if(1==number || number==2)
            correct = true;
        }
        System.out.println();
        return number;
    }
}

