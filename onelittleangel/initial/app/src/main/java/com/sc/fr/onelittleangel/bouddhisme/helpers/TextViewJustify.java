package com.sc.fr.onelittleangel.bouddhisme.helpers;

import android.graphics.Paint;
import android.widget.TextView;

/* JADX INFO: loaded from: classes.dex */
public class TextViewJustify {
    static final float COMPLEXITY = 6.0f;
    static final String SYSTEM_NEWLINE = "\n";
    static final Paint p = new Paint();

    public static void justifyText(TextView tv, float origWidth) {
        String s = tv.getText().toString();
        p.setTypeface(tv.getTypeface());
        String[] splits = s.split(SYSTEM_NEWLINE);
        for (int x = 0; x < splits.length; x++) {
            if (p.measureText(splits[x]) > origWidth) {
                splits[x] = wrap(splits[x], origWidth, p);
                String[] microSplits = splits[x].split(SYSTEM_NEWLINE);
                for (int y = 0; y < microSplits.length - 1; y++) {
                    microSplits[y] = justify(removeLast(microSplits[y], " "), origWidth, p);
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
            if (index == 0) {
                return s.substring(1);
            }
            if (index == s.length() - 1) {
                return s.substring(0, index);
            }
            return String.valueOf(s.substring(0, index)) + s.substring(indexEnd);
        }
        return s;
    }

    private static String justifyOperation(String s, float width, Paint p2) {
        float holder;
        double dRandom = Math.random();
        while (true) {
            holder = (float) (dRandom * 6.0d);
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
