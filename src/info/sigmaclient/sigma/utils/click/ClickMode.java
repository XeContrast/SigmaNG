package info.sigmaclient.sigma.utils.click;

import java.util.Random;

public class ClickMode {

    // BUTTERFLY 点击模式
    public static int[] butterflyClick(int cps) {
        Random random = new Random();
        int[] clickArray = new int[20];
        int clicks = cps;

        while (sumClickArray(clickArray) < clicks) {
            int[] indices = findZeroClickIndices(clickArray);

            if (indices.length > 0) {
                int randomIndex = random.nextInt(indices.length);
                clickArray[indices[randomIndex]] = random.nextInt(1, 3);
            } else {
                clickArray[random.nextInt(clickArray.length)]++;
            }
        }

        return clickArray;
    }

    // Drag 点击模式
    public static int[] dragClick(int cps) {
        Random random = new Random();
        int clicks = cps;

        int travelTime = random.nextInt(7, 12);
        int travelReturnTime = random.nextInt(2, 4);

        int[] clickArray = new int[travelTime + travelReturnTime];

        while (sumClickArray(clickArray) < clicks) {
            int index = findLowestClickIndex(clickArray, travelTime);
            clickArray[index]++;
        }

        return clickArray;
    }

    // Stabilized 点击模式
    public static int[] stabilizedClick(int cps) {

        int clicks = cps;
        int[] clickArray = new int[20];

        int interval = clickArray.length / clicks;
        int remainder = clickArray.length % clicks;

        int currentIndex = 0;

        for (int i = 0; i < clicks; i++) {
            if (remainder > 0) {
                clickArray[currentIndex]++;
                currentIndex += interval;
                currentIndex++;
                remainder--;
                continue;
            }

            int indexWithLeastClicks = findIndexWithLeastClicks(clickArray);
            clickArray[indexWithLeastClicks]++;
        }

        return clickArray;
    }

    // DoubleClick 点击模式
    public static int[] doubleClick(int cps) {
        Random random = new Random();
        int[] clickArray = new int[20];
        int clicks = cps;

        for (int i = 0; i < clicks; i++) {
            clickArray[random.nextInt(clickArray.length)] += 2;
        }

        return clickArray;
    }


    public static int[] normalClick(int cps) {
        int[] clickArray = new int[cps];
        int clicks = cps;

        for (int i = 0; i < clicks; i++) {
            clickArray[i] += 1;
        }

        return clickArray;
    }

    private static int findIndexWithLeastClicks(int[] clickArray) {
        int minCount = Integer.MAX_VALUE;
        int minIndex = -1;

        for (int i = 0; i < clickArray.length; i++) {
            if (clickArray[i] < minCount) {
                minCount = clickArray[i];
                minIndex = i;
            }
        }

        return minIndex;
    }

    private static int sumClickArray(int[] clickArray) {
        int sum = 0;
        for (int click : clickArray) {
            sum += click;
        }
        return sum;
    }

    private static int findLowestClickIndex(int[] clickArray, int travelTime) {
        int minCount = Integer.MAX_VALUE;
        int minIndex = -1;

        for (int i = 0; i < travelTime; i++) {
            if (clickArray[i] < minCount) {
                minCount = clickArray[i];
                minIndex = i;
            }
        }

        return minIndex;
    }

    private static int[] findZeroClickIndices(int[] clickArray) {
        int count = 0;
        for (int click : clickArray) {
            if (click == 0) {
                count++;
            }
        }

        int[] indices = new int[count];
        int index = 0;
        for (int i = 0; i < clickArray.length; i++) {
            if (clickArray[i] == 0) {
                indices[index++] = i;
            }
        }

        return indices;
    }

    public static void main(String[] args) {
        int cps = 15;  // 示例 CPS 值

        int[] butterflyResult = butterflyClick(cps);
        int[] dragResult = dragClick(cps);
        int[] stabilizedResult = stabilizedClick(cps);
        int[] doubleClickResult = doubleClick(cps);

        // 打印结果
        System.out.println("BUTTERFLY: " + java.util.Arrays.toString(butterflyResult));
        System.out.println("Drag: " + java.util.Arrays.toString(dragResult));
        System.out.println("Stabilized: " + java.util.Arrays.toString(stabilizedResult));
        System.out.println("DoubleClick: " + java.util.Arrays.toString(doubleClickResult));
    }
}