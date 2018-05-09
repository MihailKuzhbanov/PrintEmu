package printemu;
import java.util.ArrayList;
import java.util.*;

public class PrintEmu {

    /**
     * @param args the command line arguments
     */
    
    Boolean runs;
    
    ArrayList<DOC> docs;
    
    public class SecondThread extends Thread
    {
        SecondThread(ArrayList<DOC> arrDoc, String name, int size, int time)
        {
            DOC doc = new DOC(name,arrDoc.size(),size,time);
            arrDoc.add(doc);
        }

    }
    
    public void IncomeDoc(ArrayList<DOC> arrDoc, String name, int size, int time)
    {
        //добавление в очередь печати, в отдельном потоке
        SecondThread sec = new SecondThread(arrDoc,name,size,time);
    }
    
    public void CancelDoc(ArrayList<DOC> arrDoc, String name)
    {
        //удаление из очереди печати по id
        arrDoc.remove(arrDoc.indexOf(name));
    }
    
    public void PrintDoc(DOC doc)
    {
        //функция заглушка: обращение к принтеру и вывод на печать
    }
    
    public void PrintLose(ArrayList<DOC> arrDoc)
    {
        ////функция заглушка: обращение к принтеру и вывод на печать
    }
    
    public void Printing(ArrayList<DOC> arrDoc, Boolean runState)
    {
        if (runState == false)
        {
        for (DOC doc:arrDoc)
        {
            if (doc.state == 1)
            doc.state = 2;
            PrintDoc(doc);
            try        
            {
                Thread.sleep(doc.time);
            } 
            catch(InterruptedException ex) 
            {
                Thread.currentThread().interrupt();
            }
            doc.state = 3; 
        }
        System.out.print("Печать окончена");
        runState = false;
        }
        else 
        {
            System.out.print("Печать уже запущена");
        }
    }
    
    
    public static ArrayList<DOC> Stop(ArrayList<DOC> arrDoc, Boolean runState)        //остановка печати
    {
        if (runState == true)
        {
        ArrayList<DOC> lose = new ArrayList<DOC>();
        for (DOC doc:arrDoc)
        {
            if (doc.state == 1)
            lose.add(doc);
            doc.state = 4;
        }
        runState = false;
        return lose; //список ненапечатанных документов
        }
        else 
        {
            System.out.print("Печать не запущена, список документов в очереди выведен на печать");
            return arrDoc;
        }

    }
    

    
    public interface event extends EventListener
    {
        //заглушка событий
        //зависит от необходимого метода управления
    }

    public static void main(String[] args) 
    {
        

        
        //event: press "Start" button
            //Printing(docs);
        
        //event: press "Stop" button        
            //docs = Stop(docs);
            //PrintLose(docs);
        
        //event: press "Add document" button
            //IncomeDoc(docs,name,size,time);
            //name, size, time - уникальны для каждого документа
        
        //event: press "Cancel document" button
            //CancelDoc(docs, name);
            //name - имя документа который необходимо удалить
            //вводится при удалении
        
        //event выбирается в зависимости от типа управления
        //KeyEvent - при управлении с физического интерфейса (клавиатура)
        //TextEvent - при управлении командой (порт)
    }
    
}
