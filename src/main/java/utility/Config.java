package utility;

import java.util.prefs.Preferences;

public class Config {
	public static final String PLAYER_NAME1 = "player_name1";
	public static final String PLAYER_NAME2 = "player_name2";
	private Preferences preferences;
	
	public Config(){
		preferences = Preferences.userNodeForPackage(getClass());
	}
	
	public void putPlayerName(String config, String value){
		preferences.put(config, value);
	}
	
	public String getPlayerName(String config){
	if(config.equals(PLAYER_NAME1))	
		return preferences.get(config, "Player1");
	return preferences.get(PLAYER_NAME2, "Player2");
	}
}
