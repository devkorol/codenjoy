package com.codenjoy.dojo.services.generator.manual;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2012 - 2022 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import com.codenjoy.dojo.services.properties.GameProperties;
import com.codenjoy.dojo.utils.FilePathUtils;
import com.codenjoy.dojo.utils.PrintUtils;
import com.codenjoy.dojo.utils.SmokeUtils;
import com.google.common.base.Strings;

import java.io.File;
import java.util.*;

import static com.codenjoy.dojo.services.generator.ElementGenerator.getBase;
import static com.codenjoy.dojo.utils.PrintUtils.Color.*;

public class ManualGenerator {

    // используется для тестирования, этим флагом отключаем реальное сохранение файлов
    public static boolean READONLY = false;

    private static final String GLOBAL_SOURCES = "games/engine/src/main/resources/manuals/";
    private static final String GAME_SOURCES = "games/${game}/src/main/webapp/resources/${game}/help/";

    private static final String FILE_SEPARATOR = "\n\n";
    private static final String TARGET_FILE = "${path}${manualType}-${language}.md";
    private static final String GAME = "${game}";
    private static final String GLOBAL = "${global}";
    private static final String LANGUAGE = "${language}";
    private static final String PATH = "${path}";
    private static final String MANUAL_TYPE = "${manualType}";
    private static final String SLASH = "/";
    private static final String NOT_EDIT_NOTICE = "<!-- Code generated by ManualGeneratorRunner.java\n  !!!DO NOT EDIT!!! -->\n";

    private String game;
    private String language;
    private String base;
    private String globalSources;
    private String gameSources;
    private String manualType;
    private Map<String, String> settings;

    public ManualGenerator(String game, String language, String base) {
        this(game, language, base, GLOBAL_SOURCES, GAME_SOURCES);
    }

    public ManualGenerator(String game, String language, String inputBase, String globalSources, String gameSources) {
        this.game = game;
        this.language = language;
        this.base = getBase(inputBase);
        this.globalSources = globalSources;
        this.gameSources = gameSources;
        this.settings = loadSettings();
    }

    private Map<String, String> loadSettings() {
        GameProperties properties = new GameProperties(false);
        if (!properties.load(base, Locale.forLanguageTag(language), game)) {
            return new LinkedHashMap<>();
        }

        return properties.properties();
    }

    /**
     * Перечень и порядок файлов, которые участвуют в сборке мануала.
     * Список должен содержать только имя файла без всяких дополнительных тегов.
     * Порядок работы с файлом такой, мы его ищем в:
     * 1. в директории с игрой, с привязкой к языку
     * 2. в директории с игрой, но без привязки к языку
     * 3. в глобальной папке с привязкой к языку
     * 4. в глобальной папке, без привязки к языку.
     */
    protected List<String> getManualParts() {
        return Arrays.asList(
                "header.md",
                "intro.${manualType}.md",
                "game-about.md",
                "how-connect.${manualType}.md",
                "message-format.md",
                "field.md",
                "board-link.md",
                "elements.md",
                "what-to-do.md",
                "settings-intro.md",
                "settings.md",
                "faq.md",
                "client-and-api.md",
                "api-methods.md",
                "how-to-host.md",
                "how-to-host-contacts.${manualType}.md"
        );
    }

    protected String getTargetFileTemplate() {
        return TARGET_FILE;
    }

    public void generate(String manualType) {
        this.manualType = manualType;
        String target = getTargetFile();
        createSettingsFile();

        List<String> paths = getPreparedManualPartPaths();

        if (paths.size() != getManualParts().size()) {
            PrintUtils.printf("[ERROR] Can't find resources for manualType{%s}, " +
                            "game{%s}, language{%s}",
                    ERROR, manualType, game, language);
            return;
        }
        String data = build(paths);
        save(target, data);
    }

    private List<String> getPreparedManualPartPaths() {
        List<String> result = new ArrayList<>();
        for (String fileName : getManualParts()) {
            fileName = fileName.replace(MANUAL_TYPE, manualType);
            String found = lookingFor(fileName);
            if (found != null) {
                result.add(found);

                PrintUtils.printf("Found part: %s", TEXT, found);
            } else {
                PrintUtils.printf("File is missing: %s", WARNING, fileName);
            }
        }
        return result;
    }

    private String lookingFor(String fileName) {
        for (String fileMask : getFilePathVariants(fileName)) {
            String path = createPathToFile(fileMask);
            if (new File(path).exists()) {
                return path;
            }
        }
        return null;
    }

    private List<String> getFilePathVariants(String fileName) {
        return Arrays.asList(
                GAME + LANGUAGE + fileName,
                GAME + fileName,
                GLOBAL + LANGUAGE + fileName,
                GLOBAL + fileName,
                GAME + "en" + SLASH + fileName); // TODO берем default english
    }

    private String build(List<String> paths) {
        StringBuilder result = new StringBuilder();
        result.append(notificationText());
        for (String path : paths) {
            String part = load(path);
            if (Strings.isNullOrEmpty(part)) {
                continue;
            }

            part = part.replace(GAME, game);

            for (Map.Entry<String, String> entry : settings.entrySet()) {
                String replacement = "${" + entry.getKey() + "}";
                if (part.contains(replacement)) {
                    part = part.replace(replacement, entry.getValue());
                }
            }

            result.append(part);
            result.append(FILE_SEPARATOR);
        }
        return result.toString();
    }

    private String notificationText() {
        return NOT_EDIT_NOTICE;
    }

    private String makeAbsolutePath(String base, String additional) {
        return FilePathUtils.normalize(new File(base + SLASH + additional).getAbsolutePath() + SLASH);
    }

    private String makePathToGameFolder() {
        return makeAbsolutePath(base, gameSources.replace(GAME, game));
    }

    private String makePathToGlobalFolder() {
        return makeAbsolutePath(base, globalSources);
    }

    private String createPathToFile(String fileMask) {
        return fileMask
                .replace(GLOBAL, makePathToGlobalFolder())
                .replace(GAME, makePathToGameFolder())
                .replace(LANGUAGE, language + SLASH);
    }

    private String getTargetFile() {
        return getTargetFileTemplate()
                .replace(PATH, makePathToGameFolder())
                .replace(LANGUAGE, language)
                .replace(MANUAL_TYPE, manualType + "-manual");
    }

    private String load(String path) {
        return SmokeUtils.load(new File(path));
    }

    private void save(String path, String data) {
        if (!READONLY) {
            SmokeUtils.saveToFile(new File(path), data);
        }

        PrintUtils.printf("Store '%s:%s:%s' in file: '%s'", TEXT,
                manualType, game, language, path);
    }

    private void createSettingsFile() {
        String filePath = makePathToGameFolder() + language + SLASH + "settings.md";

        if (settings.isEmpty()) {
            PrintUtils.printf("The game properties is empty", WARNING);
            return;
        }
        writeSettings(filePath);
    }

    private void writeSettings(String filePath) {
        String prefix = "game.${game}.settings.";
        prefix = prefix.replace(GAME, game);
        StringBuilder data = new StringBuilder();

        data.append("| Action | Settings name |\n" + "|--------|---------------|\n");
        for (Map.Entry<String, String> entry : settings.entrySet()) {
            if (entry.getKey().contains(prefix)) {
                data.append("| ")
                    .append(entry.getValue())
                    .append(" | ")
                    .append(entry.getKey().replace(prefix, ""))
                    .append(" |\n");
            }
        }

        SmokeUtils.saveToFile(new File(filePath), data.toString());
    }
}