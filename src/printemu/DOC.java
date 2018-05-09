package printemu;

public class DOC 
{
    
    int time;                   //время печати
    int numb;                   //уникальный номер документа
    String name;                //тип документа
    int size;                   //размер формата
    int state = 0;              //состояние документа:
                                //0 - не существует
                                //1 - в очереди
                                //2 - печатается
                                //3 - напечатан
                                //4 - отменен
                                //5 - удален
    
    public DOC (String nameIn, int numbIn, int timeIn, int sizeIn)
    {
        this.name = nameIn;
        this.numb = numbIn;
        this.size = sizeIn;
        this.time = timeIn;
          
    }
}
