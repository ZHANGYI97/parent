package com.ziyi.common.stream;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * auther:jurzis
 * date: 2021/2/24 15:09
 */
public class StreamFilter {

    public static void main(String[] args) {
        List<User> list = new ArrayList<>();
        list.add(new User(1,"zhy"));
        list.add(new User(2, "zhy1"));
        list.add(new User(3, "zhy2"));
        list.add(new User(4, "zhy3"));

        List<User> filterUser = list.stream()
                .filter(user -> user.getAge() < 3)
                .collect(toList());
        System.out.println(filterUser);
    }

}
