package mongodb.failover;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

public class FailoverTest {

    
    private Properties props;
    private DocsGenerator docGen;
    private MongoClient mc;
    
    public FailoverTest(Properties props){
        this.props = props;
        
        String hosts = this.props.getProperty("hosts");
        
        List<ServerAddress> addrs = parseListOfServers(hosts);
        
        this.mc = new MongoClient(addrs);
        
        
        this.docGen = new DocsGenerator(fields);
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
    
    
    
    
    
}
