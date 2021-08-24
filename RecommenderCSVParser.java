package sol;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import src.IAttributeDatum;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class RecommenderCSVParser<T extends IAttributeDatum> {


    /**
     * @param c         the class to instantiate using the CSV data.
     * @param filepath  - the path to the CSV file relative to the project root
     *                  directory.
     * @param delimeter - the character separating the data in the CSV file,
     *                  likely ',' or ';'
     * @param header    - true if there is a header in the CSV file detailing
     *                  the column names. The header must be only one line, so
     *                  if there are two headers in the CSV file (such as the
     *                  names and types of columns) delete one of them.
     * @return - a List
     * @throws FileNotFoundException
     * @throws IOException
     */
    public List<T> parse(Class<T> c, String filepath, char delimeter,
                         boolean header) {
        Type[] interfaces = c.getInterfaces();
        boolean impsInterface = false;
        for (Type t : interfaces) {
            if (t.equals(IAttributeDatum.class)) {
                impsInterface = true;
                break;
            }
        }
        if (!impsInterface) {
            throw new RuntimeException("Class must implement IAttributeDatum");
        }
        Constructor<T> constructor = null;
        try {
            constructor = c.getConstructor(String[].class);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(
                    "Input class does not have a constructor that accepts a String "
                            + "array");
        }
        CSVFormat format = null;
        if (header) {
            format = CSVFormat.RFC4180.withHeader().withDelimiter(delimeter);
        } else {
            format = CSVFormat.RFC4180.withDelimiter(delimeter);
        }
        CSVParser parser = null;
        try {
            parser = new CSVParser(new FileReader(filepath), format);
        } catch (IOException e) {
            throw new RuntimeException(
                    "The filepath specified could not be found (" + filepath + ")");
        }
        LinkedList<T> objs = new LinkedList<>();
        for (CSVRecord record : parser) {
            String[] constructorArgs = new String[record.size()];
            for (int i = 0; i < record.size(); i++) {
                constructorArgs[i] = record.get(i);
            }

            T o = null;
            try {
                Object[] params = {constructorArgs};
                o = (T) constructor.newInstance(params);
            } catch (InvocationTargetException | IllegalAccessException | InstantiationException e) {
                System.out.println(
                        "Failed to instantiate class " + c.toString() + " with args "
                                + Arrays.toString(constructorArgs));
            }
            if (o != null) {
                objs.add(o);
            }
        }

        return objs;
    }

}


