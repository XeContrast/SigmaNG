package info.sigmaclient.sigma.sigma5.utils;

public class 꿩婯㐖쥦꿩 {
    private static String[] 䄟Ꮤ轐曞䕦;
    public float 韤㕠䩉鷏敤;
    public float 蒕泹ಽ堍붃;
    public float 堍綋ᙽ埙杭;
    public float Ⱋ붛掬묙뚔;

    public 꿩婯㐖쥦꿩(final float 韤㕠䩉鷏敤, final float 蒕泹ಽ堍붃, final float 堍綋ᙽ埙杭) {
        this.韤㕠䩉鷏敤 = 韤㕠䩉鷏敤;
        this.蒕泹ಽ堍붃 = 蒕泹ಽ堍붃;
        this.堍綋ᙽ埙杭 = 堍綋ᙽ埙杭;
        this.Ⱋ붛掬묙뚔 = 0.1f;
    }

    public 꿩婯㐖쥦꿩(final float n, final float n2, final float n3, final float ⱋ붛掬묙뚔) {
        this(n, n2, n3);
        this.Ⱋ붛掬묙뚔 = ⱋ붛掬묙뚔;
    }

    public float 鷏蚳甐嶗轐(final float n) {
        if (n != this.韤㕠䩉鷏敤) {
            this.韤㕠䩉鷏敤 += (n - this.韤㕠䩉鷏敤) * this.Ⱋ붛掬묙뚔;
        }
        return this.韤㕠䩉鷏敤;
    }

    public float 㥇掬卫曞ᔎ(final float n) {
        if (n != this.蒕泹ಽ堍붃) {
            this.蒕泹ಽ堍붃 += (n - this.蒕泹ಽ堍붃) * this.Ⱋ붛掬묙뚔;
        }
        return this.蒕泹ಽ堍붃;
    }

    public float 鷏躚騜콗婯(final float n) {
        if (n != this.堍綋ᙽ埙杭) {
            this.堍綋ᙽ埙杭 += (n - this.堍綋ᙽ埙杭) * this.Ⱋ붛掬묙뚔;
        }
        return this.堍綋ᙽ埙杭;
    }
}
    