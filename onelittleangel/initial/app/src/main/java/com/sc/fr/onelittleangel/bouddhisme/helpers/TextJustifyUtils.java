package com.sc.fr.onelittleangel.bouddhisme.helpers;

import android.graphics.Paint;
import android.widget.TextView;

/* JADX INFO: loaded from: classes.dex */
public class TextJustifyUtils {
    static final float COMPLEXITY = 5.12f;
    static final String SYSTEM_NEWLINE = "\n";
    static final Paint p = new Paint();

    public static void justify(TextView textView) {
        Paint paint = new Paint();
        String smb = "";
        paint.setColor(textView.getCurrentTextColor());
        paint.setTypeface(textView.getTypeface());
        paint.setTextSize(textView.getTextSize());
        float textWrapWidth = textView.getWidth();
        float spaceOffset = paint.measureText(" ");
        String[] blocks = textView.getText().toString().split("((?<=\n)|(?=\n))");
        if (textWrapWidth >= 20.0f) {
            int i = 0;
            while (i < blocks.length) {
                String block = blocks[i];
                if (block.length() != 0) {
                    if (block.equals(SYSTEM_NEWLINE)) {
                        smb = String.valueOf(smb) + block;
                    } else {
                        String block2 = block.trim();
                        if (block2.length() != 0) {
                            Object[] wrappedObj = createWrappedLine(block2, paint, spaceOffset, textWrapWidth);
                            String wrappedLine = (String) wrappedObj[0];
                            float wrappedEdgeSpace = ((Float) wrappedObj[1]).floatValue();
                            String[] lineAsWords = wrappedLine.split(" ");
                            int spacesToSpread = (int) (wrappedEdgeSpace != Float.MIN_VALUE ? wrappedEdgeSpace / spaceOffset : 0.0f);
                            for (String word : lineAsWords) {
                                smb = String.valueOf(smb) + word + " ";
                                spacesToSpread--;
                                if (spacesToSpread > 0) {
                                    smb = String.valueOf(smb) + " ";
                                }
                            }
                            smb = smb.trim();
                            if (blocks[i].length() > 0) {
                                blocks[i] = blocks[i].substring(wrappedLine.length());
                                if (blocks[i].length() > 0) {
                                    smb = String.valueOf(smb) + SYSTEM_NEWLINE;
                                }
                                i--;
                            }
                        }
                    }
                }
                i++;
            }
            textView.setGravity(3);
            textView.setText(smb);
        }
    }

    protected static Object[] createWrappedLine(String block, Paint paint, float spaceOffset, float maxWidth) {
        String line = "";
        for (String word : block.split("\\s")) {
            float cacheWidth = paint.measureText(word);
            float maxWidth2 = maxWidth - cacheWidth;
            if (maxWidth2 <= 0.0f) {
                return new Object[]{line, Float.valueOf(maxWidth2 + cacheWidth + spaceOffset)};
            }
            line = String.valueOf(line) + word + " ";
            maxWidth = maxWidth2 - spaceOffset;
        }
        if (paint.measureText(block) <= maxWidth) {
            return new Object[]{block, Float.valueOf(Float.MIN_VALUE)};
        }
        return new Object[]{line, Float.valueOf(maxWidth)};
    }

    public static void run(TextView tv, float origWidth) {
        String s = tv.getText().toString();
        p.setTypeface(tv.getTypeface());
        String[] splits = s.split(SYSTEM_NEWLINE);
        float width = origWidth - 5.0f;
        for (int x = 0; x < splits.length; x++) {
            if (p.measureText(splits[x]) > width) {
                splits[x] = wrap(splits[x], width, p);
                String[] microSplits = splits[x].split(SYSTEM_NEWLINE);
                for (int y = 0; y < microSplits.length - 1; y++) {
                    microSplits[y] = justify(removeLast(microSplits[y], " "), width, p);
                }
                StringBuilder smb_internal = new StringBuilder();
                for (int z = 0; z < microSplits.length; z++) {
                    smb_internal.append(String.valueOf(microSplits[z]) + (z + 1 < microSplits.length ? SYSTEM_NEWLINE : ""));
                }
                splits[x] = smb_internal.toString();
            }
        }
        StringBuilder smb = new StringBuilder();
        for (String cleaned : splits) {
            smb.append(String.valueOf(cleaned) + SYSTEM_NEWLINE);
        }
        tv.setGravity(3);
        tv.setText(smb);
    }

    private static String wrap(String s, float width, Paint p2) {
        String[] str = s.split("\\s");
        StringBuilder smb = new StringBuilder();
        smb.append(SYSTEM_NEWLINE);
        for (int x = 0; x < str.length; x++) {
            float length = p2.measureText(str[x]);
            String[] pieces = smb.toString().split(SYSTEM_NEWLINE);
            try {
                if (p2.measureText(pieces[pieces.length - 1]) + length > width) {
                    smb.append(SYSTEM_NEWLINE);
                }
            } catch (Exception e) {
            }
            smb.append(String.valueOf(str[x]) + " ");
        }
        return smb.toString().replaceFirst(SYSTEM_NEWLINE, "");
    }

    private static String removeLast(String s, String g) {
        if (s.contains(g)) {
            int index = s.lastIndexOf(g);
            int indexEnd = index + g.length();
            return index == 0 ? s.substring(1) : index == s.length() + (-1) ? s.substring(0, index) : String.valueOf(s.substring(0, index)) + s.substring(indexEnd);
        }
        return s;
    }

    private static String justifyOperation(String s, float width, Paint p2) {
        float holder;
        double dRandom = Math.random();
        while (true) {
            holder = (float) (dRandom * 5.119999885559082d);
            if (!s.contains(Float.toString(holder))) {
                break;
            }
            dRandom = Math.random();
        }
        String holder_string = Float.toString(holder);
        float lessThan = width;
        for (int current = 0; p2.measureText(s) < lessThan && current < 100; current++) {
            s = s.replaceFirst(" ([^" + holder_string + "])", " " + holder_string + "$1");
            lessThan = (p2.measureText(holder_string) + lessThan) - p2.measureText(" ");
        }
        String cleaned = s.replaceAll(holder_string, " ");
        return cleaned;
    }

    private static String justify(String s, float width, Paint p2) {
        while (p2.measureText(s) < width) {
            s = justifyOperation(s, width, p2);
        }
        return s;
    }
}
