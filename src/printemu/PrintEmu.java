package printemu;
import java.util.ArrayList;
import java.util.*;

public class PrintEmu {

    /**
     * @param args the command line arguments
     */
    
    //Boolean runs;
    
    //ArrayList<DOC> docs;
    
    public static class SecondThread extends Thread
    {
        SecondThread(ArrayList<DOC> arrDoc, String name, int size, int time)
        {
            DOC doc = new DOC(name,arrDoc.size(),size,time);
            arrDoc.add(doc);
        }

    }
    
    public static void IncomeDoc(ArrayList<DOC> arrDoc, String name, int size, int time)
    {
        //добавление в очередь печати, в отдельном потоке
        SecondThread sec = new SecondThread(arrDoc,name,size,time);
    }
    
    public static void CancelDoc(ArrayList<DOC> arrDoc, String name)
    {
        //удаление из очереди печати по id
        arrDoc.remove(arrDoc.indexOf(name));
    }
    
    public static void PrintDoc(DOC doc)
    {
        //функция заглушка: обращение к принтеру и вывод на печать
    }
    
    public static void PrintLose(ArrayList<DOC> arrDoc)
    {
        //функция заглушка: обращение к принтеру и вывод на печать
    }
    
    public static void Printing(ArrayList<DOC> arrDoc, Boolean runState)
    {
        //печать
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
    
    public static int AveragePrinted(ArrayList<DOC> arrDoc)
    {
        //среднее время напечатанных
        int avgTime=0;
        int count=0;
        for (DOC doc:arrDoc)
        {
            if (doc.state == 3)
            {
                avgTime += doc.time;
                count++;
            }
        }
        return (avgTime/count);
    }
    
    
    
   
    public static ArrayList<DOC> AlreadyPrinted(ArrayList<DOC> arrDoc, String type)
    {
        //вывод напечатанных с сортировкой
        ArrayList<DOC> goodList = null;
        for (DOC doc:arrDoc)
        {
            if (doc.state == 3)
            {
                goodList.add(doc);
            }
        }
        //тут должна быть сортировка
        //по типу, указываемому в конструкторе функции как type
        return goodList;
    }
    
    public static ArrayList<DOC> Stop(ArrayList<DOC> arrDoc, Boolean runState)        //остановка печати
    {
        //остановка печати
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
        //интерфейс слушателей
        void Started();
        void Stopped();
        void Added();
        void Canceled();
        void Average();
        void Printed();

    }
    
    class REACT 
    {
        //слушатель
        private List<event> listener = new ArrayList<event>();
        public void addListener(event toAdd)
        {
            listener.add(toAdd);
        }

}
    static class COM implements event
        {
        //обработчик событий
        public ArrayList<DOC> arr = null;
        public Boolean runs=false;
        public String name;
        public int size, time;
        public COM (ArrayList<DOC> arrDoc, Boolean run)
        {
            this.runs = run;
            this.arr = arrDoc;
        }
            @Override
            public void Started()
            {
                Printing(arr,runs);
            }
            @Override
            public void Stopped()
            {
                arr = Stop(arr,runs);
                PrintLose(arr);
            }
            @Override
            public void Added()
            {
                IncomeDoc(arr,name,size,time);
            }
            @Override
            public void Canceled()
            {
                CancelDoc(arr, name);
            }
            @Override
            public void Average()
            {
                AveragePrinted(arr);
            }
            @Override
            public void Printed()
            {
                AlreadyPrinted(arr,"name");//при сортировке по имени
            }
        }
    public static void main(String[] args) 
    {
        ArrayList<DOC> docs = null;
        Boolean runs=false;
        
        COM command = new COM(docs, runs);
    }
    
}
