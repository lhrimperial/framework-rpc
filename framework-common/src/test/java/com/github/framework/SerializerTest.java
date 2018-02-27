package com.github.framework;

import com.github.framework.common.serialization.SerializerHolder;

/**
 *
 */
public class SerializerTest {
    public static void main(String[] args){
        UserInfo userInfo = new UserInfo(1, "andy", 28);
        byte[] data = SerializerHolder.serializerImpl().writeObject(userInfo);
        System.out.println(data.length);
        UserInfo userInfo1 = SerializerHolder.serializerImpl().readObject(data, UserInfo.class);
        System.out.println(userInfo1);
    }

    private static class UserInfo {
        private Integer id;
        private String name;
        private int age;

        public UserInfo(Integer id, String name, int age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "UserInfo{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
