package mongodb.failover;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DocsGenerator {

    
    private String[] fields;
    private Random rand;
    
    private static final char[] symbols = new char[36];

    static {
      for (int idx = 0; idx < 10; ++idx)
        symbols[idx] = (char) ('0' + idx);
      for (int idx = 10; idx < 36; ++idx)
        symbols[idx] = (char) ('a' + idx - 10);
    }
    /**
     * DBObjects generator for a set of fields.
     * @param fields
     */
    public DocsGenerator(String ... fields){
        
        this.fields = fields;
        this.rand = new Random();
    }
    
    
    public Map<String, String> generateDoc(){
        
        Map<String, String> doc = new HashMap<String, String>();
        
        int valueLength = 10;
        
        for ( String s : this.fields){
            doc.put(s, generateRandValue(valueLength));
        }
        
        return doc;
        
    }
    
    private String generateRandValue(int length){
        char[] buf = new char[length];
        for (int idx = 0; idx < buf.length; ++idx) 
            buf[idx] = symbols[this.rand.nextInt(symbols.length)];
          return new String(buf);
    }
    
    
}
