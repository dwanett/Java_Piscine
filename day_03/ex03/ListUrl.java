import java.util.List;

public class ListUrl {
	private static List<String[]> listURL;


	public ListUrl(List<String[]> listURL) {
		ListUrl.listURL = listURL;
	}

	public synchronized String[] getNextUri(){
		String[] Url = null;

		if (listURL.size() != 0) {
			Url = listURL.get(0);
			listURL.remove(0);
		}
		return Url;
	}
}
