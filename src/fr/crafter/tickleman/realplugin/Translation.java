package fr.crafter.tickleman.realplugin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

//##################################################################################### Translation
public class Translation
{

	private final String            fileName;
	private final JavaPlugin        plugin;
	private HashMap<String, String> translations = new HashMap<String, String>();

	// ---------------------------------------------------------------------------------- Translation
	public Translation(final JavaPlugin plugin)
	{
		this(plugin, "en");
	}

	// ---------------------------------------------------------------------------------- Translation
	public Translation(final JavaPlugin plugin, final String language)
	{
		this.plugin = plugin;
		this.fileName = plugin.getDataFolder().getPath() + "/" + language + ".lang.txt";
	}

	// ----------------------------------------------------------------------------------------- load
	public Translation load()
	{
		translations.clear();
		if (!FileTools.fileExists(fileName)) {
			FileTools.extractDefaultFile(plugin, fileName);
		}
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String buffer;
			while ((buffer = reader.readLine()) != null) {
				if ((buffer.length() > 0) && (buffer.charAt(0) != '#')) {
					String[] line = buffer.split("=");
					if (line.length >= 2) {
						String key = line[0];
						String value = line[1];
						if (!key.equals("") && !value.equals("")) {
							translations.put(key, value);
						}
					}
				}
			}
			reader.close();
		} catch (Exception e) {
			if (fileName.contains("/en.lang.txt")) {
				plugin.getServer().getLogger().log(
					Level.INFO, "You can create " + fileName + " to change texts"
				);
			} else {
				plugin.getServer().getLogger().log(
					Level.WARNING, "Missing " + fileName + " (check your language configuration option)"
				);
			}
		}
		return this;
	}

	// ------------------------------------------------------------------------------------------- tr
	public String tr(String text)
	{
		String translated = translations.get(text);
		if ((translated == null) || (translated.equals(""))) {
			return text;
		} else {
			return translated;
		}
	}

}