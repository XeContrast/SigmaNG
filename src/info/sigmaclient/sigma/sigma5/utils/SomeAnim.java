package info.sigmaclient.sigma.sigma5.utils;

import java.util.ArrayList;
import java.util.List;

public class SomeAnim {


    public static class 뗴㞈泹躚杭 {
        private double 펊䢶쇼甐㥇;
        private double 浦岋陬洝眓;

        public 뗴㞈泹躚杭() {
            this.펊䢶쇼甐㥇 = 0.0;
            this.浦岋陬洝眓 = 0.0;
        }

        public 뗴㞈泹躚杭(final double 펊䢶쇼甐㥇, final double 浦岋陬洝眓) {
            this.펊䢶쇼甐㥇 = 펊䢶쇼甐㥇;
            this.浦岋陬洝眓 = 浦岋陬洝眓;
        }

        public double 堧筕瀳핇阢뚔() {
            return this.펊䢶쇼甐㥇;
        }

        public double 醧ኞ좯곻泹嶗() {
            return this.浦岋陬洝眓;
        }
    }

    public static float 欫좯콵甐鶲㥇(final float n, final double... array) {
        final ArrayList list = new ArrayList();
        list.add(new 뗴㞈泹躚杭(0.0, 0.0));
        list.add(new 뗴㞈泹躚杭(array[0], array[1]));
        list.add(new 뗴㞈泹躚杭(array[2], array[3]));
        list.add(new 뗴㞈泹躚杭(1.0, 1.0));
        return (float)new 햖롤待쬷綋(0.0055555556900799274).捉岋揩ศ藸늦(list, n);
    }
    public static class 햖롤待쬷綋 {
        private double 䢶弻붃陬랾;
        public static final double 褕郝贞曞ꁈ = 0.10000000149011612;

        public 햖롤待쬷綋(final double 䢶弻붃陬랾) {
            if (䢶弻붃陬랾 > 0.0 && 䢶弻붃陬랾 < 1.0) {
                this.䢶弻붃陬랾 = 䢶弻붃陬랾;
                return;
            }
        }

        public 햖롤待쬷綋() {
            this(0.10000000149011612);
        }

        public 뗴㞈泹躚杭 婯뫤鞞꿩㐖卒(final 뗴㞈泹躚杭 뗴㞈泹躚杭, final 뗴㞈泹躚杭 뗴㞈泹躚杭2, final 뗴㞈泹躚杭 뗴㞈泹躚杭3, final double n) {
            return new 뗴㞈泹躚杭((1.0 - n) * (1.0 - n) * 뗴㞈泹躚杭.堧筕瀳핇阢뚔() + 2.0 * n * (1.0 - n) * 뗴㞈泹躚杭2.堧筕瀳핇阢뚔() + n * n * 뗴㞈泹躚杭3.堧筕瀳핇阢뚔(), (1.0 - n) * (1.0 - n) * 뗴㞈泹躚杭.醧ኞ좯곻泹嶗() + 2.0 * n * (1.0 - n) * 뗴㞈泹躚杭2.醧ኞ좯곻泹嶗() + n * n * 뗴㞈泹躚杭3.醧ኞ좯곻泹嶗());
        }

        public 뗴㞈泹躚杭 騜鷏泹郝蒕ᜄ(final 뗴㞈泹躚杭 뗴㞈泹躚杭, final 뗴㞈泹躚杭 뗴㞈泹躚杭2, final 뗴㞈泹躚杭 뗴㞈泹躚杭3, final 뗴㞈泹躚杭 뗴㞈泹躚杭4, final double n) {
            final double n2 = 1.0 - n;
            final double n3 = n2 * n2;
            final double n4 = n3 * n2;
            return new 뗴㞈泹躚杭(뗴㞈泹躚杭.堧筕瀳핇阢뚔() * n4 + 뗴㞈泹躚杭2.堧筕瀳핇阢뚔() * 3.0 * n * n3 + 뗴㞈泹躚杭3.堧筕瀳핇阢뚔() * 3.0 * n * n * n2 + 뗴㞈泹躚杭4.堧筕瀳핇阢뚔() * n * n * n, 뗴㞈泹躚杭.醧ኞ좯곻泹嶗() * n4 + 뗴㞈泹躚杭2.醧ኞ좯곻泹嶗() * 3.0 * n * n3 + 뗴㞈泹躚杭3.醧ኞ좯곻泹嶗() * 3.0 * n * n * n2 + 뗴㞈泹躚杭4.醧ኞ좯곻泹嶗() * n * n * n);
        }

        public double 捉岋揩ศ藸늦(final List<뗴㞈泹躚杭> list, final float n) {
            if (n != 0.0f) {
                final List<뗴㞈泹躚杭> 挐啖꿩츚酭Ⱋ = this.挐啖꿩츚酭Ⱋ(list);
                double n2 = 1.0;
                for (int i = 0; i < 挐啖꿩츚酭Ⱋ.size(); ++i) {
                    final 뗴㞈泹躚杭 뗴㞈泹躚杭 = 挐啖꿩츚酭Ⱋ.get(i);
                    if (뗴㞈泹躚杭.堧筕瀳핇阢뚔() > n) {
                        break;
                    }
                    final double 醧ኞ좯곻泹嶗 = 뗴㞈泹躚杭.醧ኞ좯곻泹嶗();
                    뗴㞈泹躚杭 뗴㞈泹躚杭2 = new 뗴㞈泹躚杭(1.0, 1.0);
                    if (i + 1 < 挐啖꿩츚酭Ⱋ.size()) {
                        뗴㞈泹躚杭2 = 挐啖꿩츚酭Ⱋ.get(i + 1);
                    }
                    n2 = 醧ኞ좯곻泹嶗 + (뗴㞈泹躚杭2.醧ኞ좯곻泹嶗() - 뗴㞈泹躚杭.醧ኞ좯곻泹嶗()) * ((n - 뗴㞈泹躚杭.堧筕瀳핇阢뚔()) / (뗴㞈泹躚杭2.堧筕瀳핇阢뚔() - 뗴㞈泹躚杭.堧筕瀳핇阢뚔()));
                }
                return n2;
            }
            return 0.0;
        }

        public List<뗴㞈泹躚杭> 挐啖꿩츚酭Ⱋ(final List<뗴㞈泹躚杭> list) {
            if (list.size() >= 3) {
                final 뗴㞈泹躚杭 뗴㞈泹躚杭 = list.get(0);
                final 뗴㞈泹躚杭 뗴㞈泹躚杭2 = list.get(1);
                final 뗴㞈泹躚杭 뗴㞈泹躚杭3 = list.get(2);
                final 뗴㞈泹躚杭 뗴㞈泹躚杭4 = (list.size() != 4) ? null : list.get(3);
                final ArrayList list2 = new ArrayList();
                뗴㞈泹躚杭 뗴㞈泹躚杭5 = 뗴㞈泹躚杭;
                for (double n = 0.0; n < 1.0; n += this.䢶弻붃陬랾) {
                    list2.add(뗴㞈泹躚杭5);
                    뗴㞈泹躚杭5 = ((뗴㞈泹躚杭4 != null) ? this.騜鷏泹郝蒕ᜄ(뗴㞈泹躚杭, 뗴㞈泹躚杭2, 뗴㞈泹躚杭3, 뗴㞈泹躚杭4, n) : this.婯뫤鞞꿩㐖卒(뗴㞈泹躚杭, 뗴㞈泹躚杭2, 뗴㞈泹躚杭3, n));
                }
                return list2;
            }
            return null;
        }
    }
}
