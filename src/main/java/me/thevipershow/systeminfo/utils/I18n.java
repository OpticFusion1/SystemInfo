package me.thevipershow.systeminfo.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.regex.Pattern;
import me.thevipershow.systeminfo.SystemInfo;

public class I18n {

    private static I18n instance;
    private static final String MESSAGES = "messages";
    private static final Pattern NODOUBLEMARK = Pattern.compile("''");
    private static final ResourceBundle NULL_BUNDLE = new ResourceBundle() {
        @Override
        public Enumeration<String> getKeys() {
            return null;
        }

        @Override
        protected Object handleGetObject(String key) {
            return null;
        }
    };
    private final transient Locale defaultLocale = Locale.getDefault();
    private transient Locale currentLocale = defaultLocale;
    private transient Map<String, MessageFormat> messageFormatCache = new HashMap<>();
    private transient ResourceBundle customBundle;
    private transient ResourceBundle localeBundle;
    private final transient ResourceBundle defaultBundle;
    private static SystemInfo systemInfo;

    public I18n(SystemInfo systemInfo) {
        this.systemInfo = systemInfo;
        defaultBundle = ResourceBundle.getBundle(MESSAGES, Locale.ENGLISH);
        localeBundle = defaultBundle;
        customBundle = NULL_BUNDLE;
        instance = this;
    }

    public Locale getCurrentLocale() {
        return currentLocale;
    }

    private String translate(final String string) {
        try {
            try {
                return customBundle.getString(string);
            } catch (MissingResourceException ex) {
                return localeBundle.getString(string);
            }
        } catch (MissingResourceException ex) {
            systemInfo.getLogger().log(Level.WARNING, "Missing translation key {} in translation file {1}",
                    new Object[]{string, localeBundle.getLocale().toString()});
            return defaultBundle.getString(string);
        }
    }

    public static String tl(String string) {
        return tl(string, (Object[]) null);
    }

    public static String tl(final String string, final Object... objects) {
        if (instance == null) {
            return "";
        }
        if (objects == null || objects.length == 0) {
            return NODOUBLEMARK.matcher(instance.translate(string)).replaceAll("'");
        } else {
            return instance.format(string, objects);
        }
    }

    public String format(final String string, final Object... objects) {
        String format = translate(string);
        MessageFormat messageFormat = messageFormatCache.get(format);
        if (messageFormat == null) {
            try {
                messageFormat = new MessageFormat(format);
            } catch (IllegalArgumentException e) {
                systemInfo.getLogger().log(Level.SEVERE, "Invalid Translation key for '{0}':",
                        new Object[]{e.getMessage(), string});
                format = format.replaceAll("\\{(\\D*?)\\}", "\\[$1\\]");
                messageFormat = new MessageFormat(format);
            }
            messageFormatCache.put(format, messageFormat);
        }
        return messageFormat.format(objects).replace(' ', ' '); // replace nbsp with a space
    }

    public void updateLocale(final String loc) {
        if (loc != null && !loc.isEmpty()) {
            final String[] parts = loc.split("[_\\.]");
            if (parts.length == 1) {
                currentLocale = new Locale(parts[0]);
            }
            if (parts.length == 2) {
                currentLocale = new Locale(parts[0], parts[1]);
            }
            if (parts.length == 3) {
                currentLocale = new Locale(parts[0], parts[1], parts[2]);
            }
        }
        ResourceBundle.clearCache();
        messageFormatCache = new HashMap<>();

        try {
            localeBundle = ResourceBundle.getBundle(MESSAGES, currentLocale);
        } catch (MissingResourceException ex) {
            localeBundle = NULL_BUNDLE;
        }

        try {
            customBundle = ResourceBundle.getBundle(MESSAGES, currentLocale, new FileResClassLoader(I18n.class.getClassLoader()));
        } catch (MissingResourceException ex) {
            customBundle = NULL_BUNDLE;
        }
        systemInfo.getLogger().log(Level.INFO, "Using locale {0}", currentLocale.toString());
    }

    public static String capitalCase(final String input) {
        return input == null || input.length() == 0 ? input : input.toUpperCase(Locale.ENGLISH).charAt(0) + input
                .toLowerCase(Locale.ENGLISH).substring(1);
    }

    private static class FileResClassLoader extends ClassLoader {

        private final transient File dataFolder;

        FileResClassLoader(final ClassLoader classLoader) {
            super(classLoader);
            this.dataFolder = new File("AntiMalware");
        }

        @Override
        public URL getResource(final String string) {
            final File file = new File(dataFolder, string);
            if (file.exists()) {
                try {
                    return file.toURI().toURL();
                } catch (MalformedURLException ex) {
                }
            }
            return null;
        }

        @Override
        public InputStream getResourceAsStream(final String string) {
            final File file = new File(dataFolder, string);
            if (file.exists()) {
                try {
                    return new FileInputStream(file);
                } catch (FileNotFoundException ex) {
                }
            }
            return systemInfo.getResource(string);
        }
    }
}
