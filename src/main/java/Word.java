
public class Word
{
    private String word=new String();
    private int count;


    public Word(String word)
    {
        this.word=word;
        this.count=1;
    }
    public int GetCount()
    {
        return  count;
    }

    public  void IncCount()
    {
        count++;
    }
    public String getWord()
    {
        return word;
    }

    public void setWord(String word)
    {
        this.word=word;
    }

}
