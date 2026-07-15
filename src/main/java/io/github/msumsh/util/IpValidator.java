package io.github.msumsh.util;

public class IpValidator {
    public boolean validate(String ip) {
        if (ip == null || ip.isEmpty()) {
            return false;
        }

        ip = ip.trim();

        String[] nums = ip.split("\\.");
        if (nums.length != 4) {
            return false;
        }

        for (String num : nums) {
            if (num.isEmpty()) {
                return false;
            }

            int n;

            try {
                n = Integer.parseInt(num);
            } catch (NumberFormatException e) {
                return false;
            }

            if (n < 0 || n > 255) {
                return false;
            }
        }

        return true;
    }
}
