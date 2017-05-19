import java.util.ArrayList;
import java.util.LinkedList;

public class BackwardChaining extends Algorithm {
	/**
	 * @author Tim
	 *
	 */
	
	private KnowledgeBase aKnowledgeBase;
	private ArrayList<HornClause> clauses;
	private ArrayList<String> facts;
	private ArrayList<String> queries;
	private ArrayList<String> outputQueries;
	private String query;
	
	public BackwardChaining(KnowledgeBase aKb, String aToAsk) {
    super(aKb, aToAsk);
    
    clauses = aKb.getClauses();
	facts = aKb.getFacts();
	query = aToAsk;

	outputQueries = new ArrayList<String>();

    setCode("BC");
	}
	
	public String Solve(){
		
		String output = "";
		
		// CheckFacts check's whether the query can be proven 
		
		if ( CheckFacts() )
		{
			// if so, output YES:
			output = "YES: ";
			
			// as well as each fact that is discovered
			
			for ( int i = 0; i < outputFacts.size(); i++ )
			{
				output += ( outputQueries.get(i) + ", " );
			}
		}
		
		else
		{
			// else output "(Query) could not be proven"
			
			output = query + " could not be proven.";
		}
		
		return output;		
	}

	public bool CheckFacts(){
		
		// Put the query on the liat of queries

		queries.add( aState );
		
		// while there are still queries to be proven

		while ( queries.size() > 0 )
		{
			// take the first query off of queries to be checked 

			String currentQuery = queries.pop();

			// add it to the list of queries that will be used to output (if true)

			outputQueries.push( currentQuery );

			// Compare currentQuery with every fact in Facts:
			// if it matches a fact then the current loop can end

			if( ! CompareToFacts( aQuery ) )
			{
				// if not, compare currentQuery with every entailed literal
				// if it matches, add the literal(s) from the left
				// of the entailment onto queries

				if( ! CompareToClauses( aQuery ) )
				{
					// if currentQuery still didn’t find a match then 
					// the search has failed 

					return false;
				}
			}
		}

		// when queries is empty, it means there are no more queries to prove,
		// so the search was a success

		return true;
	}
	
	public boolean CompareToFacts( String aQuery )
	{
		for ( int i = 0; i < facts[i].size(); i++ )
		{
			if ( aQuery.equals( facts[i] ) )
			{
				return true;
			}
		}

		return false;
	}

	public boolean CompareToClauses( String aQuery )
	{
		boolean result = false;

		for ( int i = 0; i < clauses.size(); i++ )
		{
			if ( aQuery.equals( clauses[i].getEntailedLiteral() ) )
			{
				result = true;

				for ( int j = 0; j < clauses[i].literalCount(); j++ )
				{
					queries.push( clauses[i].getLiteralsAtIndex(j) );
				}
			}
		}

		return result;
	}
}


