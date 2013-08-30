package mongodb.failover;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;

public class FailoverTest {

    
    private Properties props;
    private DocsGenerator docGen;
    private MongoClient mc;
    private final int numDocs;
    private final String dbname;
    private final String collname;
    
    private int processedDocs=0;
    public FailoverTest(Properties props){
        this.props = props;
        
        String hosts = this.props.getProperty("hosts");
        
        List<ServerAddress> addrs = parseListOfServers(hosts);
        
        this.mc = new MongoClient(addrs);
        
        String fields = this.props.getProperty("fields");
        
        this.docGen = new DocsGenerator(parseFields(fields));
        
        this.numDocs = Integer.parseInt(this.props.getProperty("ndocs"));
        this.dbname = this.props.getProperty("dbname");
        this.collname = this.props.getProperty("collname");
        
    }

    private String[] parseFields(final String str){
    	StringTokenizer tokenizer = new StringTokenizer(str,",");
    	
    	String[] list = new String[ tokenizer.countTokens() ];
    	
    	for(int i=0; tokenizer.hasMoreTokens(); i++ ){
    		list[i] = tokenizer.nextToken();
    	}
    	return list;
    }
    
    
    private List<ServerAddress> parseListOfServers(String hosts) {
        StringTokenizer tokenizer = new StringTokenizer(hosts,",");
        
        List<ServerAddress> addrs = new ArrayList<ServerAddress>();
        
        while( tokenizer.hasMoreTokens()){
            try {
                addrs.add( new ServerAddress(tokenizer.nextToken()));
            } catch (UnknownHostException e) {
                System.out.println("Incorrect hosts setting: " + hosts);
                e.printStackTrace();
            }
        }
        return addrs;
    }
    
    
    public void run(){
    	
		DB db = mc.getDB(this.dbname);
		DBCollection coll = db.getCollection(this.collname);
    	for( ;processedDocs< this.numDocs; processedDocs++){
    		DBObject ob = new BasicDBObject( this.docGen.generateDoc()  );
    		try{
    			WriteResult wr = coll.insert(ob, WriteConcern.ACKNOWLEDGED);
    			System.out.println( "Got result "+ wr);
    		} catch (RuntimeException e){
    			System.out.println("Error: "+ e);
    		}
    		
    	}
    	
    	System.out.println("Processed documents="+processedDocs);
    	
    }

	public DocsGenerator getDocGen() {
		return docGen;
	}

	public void setDocGen(DocsGenerator docGen) {
		this.docGen = docGen;
	}

	public int getProcessedDocs() {
		return processedDocs;
	}
    
    
    
    
    
    
}
