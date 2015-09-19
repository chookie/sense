package com.chookie.sense.user;

class TwitterUser {
    private String twitterHandle;

    TwitterUser(String twitterHandle) {
        this.twitterHandle = twitterHandle;
    }

    @Override
    public String toString() {
        return twitterHandle;
    }
}
