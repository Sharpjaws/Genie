package com.djrapitops.genie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Class that contains message parts and variations to make genie more live
 * like.
 *
 * @author Rsl1122
 */
public class Messages {

    private final Map<MessageType, List<String>> messages;
    private final Random random;

    public Messages() {
        messages = new HashMap<>();
        for (MessageType type : MessageType.values()) {
            messages.put(type, new ArrayList<>());
        }
        random = new Random();
        addMessages();
    }

    public String getMessage(MessageType type) {
        List<String> list = messages.get(type);
        return list.get(getRandomNumber(list.size()));
    }

    private int getRandomNumber(int bound) {
        return random.nextInt(bound);
    }

    public void addMsg(MessageType type, String msg) {
        messages.get(type).add(msg);
    }

    private void addMessages() {
        addMsg(MessageType.SUMMON, "Behold, you have summoned me, Genie!");
        addMsg(MessageType.SUMMON, "Behold, I am the Genie of the lamp!");
        addMsg(MessageType.SUMMON, "Look at me I'm Mr. MEESEEKS!");
        addMsg(MessageType.SUMMON, "BEWARE THE WRATH OF THE OLD GENIE, Just kiddin'");
        addMsg(MessageType.SUMMON, "Thanks for letting me out of there!");
        addMsg(MessageType.SUMMON, "Thank yaa for freeing me from me lamp eh!");
        addMsg(MessageType.SUMMON, "I have slept for thousands of years, but now you woke me from my nap!");
        addMsg(MessageType.SUMMON, "What if I told you, that I am a Genie?");
        addMsg(MessageType.SUMMON, "Thank you for helping me with my geometry homework.");
        addMsg(MessageType.SUMMON, "Ok, ok, you released me from this lamp bla bla bla.");
        addMsg(MessageType.SUMMON, "How about another wish to help your situation?");
        addMsg(MessageType.SUMMON, "Who has called the BUTT GENIE?");
        addMsg(MessageType.SUMMON, "SHAZAM!");
        addMsg(MessageType.SUMMON, "KAZAAM!");
        addMsg(MessageType.SUMMON, "Alright Sparky, here's the deal.");
        addMsg(MessageType.SUMMON, "Poof! Waddya need?");
        addMsg(MessageType.FULFILL, "Your word is my command!");
        addMsg(MessageType.FULFILL, "Here you go");
        addMsg(MessageType.FULFILL, "The genie has granted your wish");
        addMsg(MessageType.FULFILL, "How about them apples?");
        addMsg(MessageType.FULFILL, "I think you might have wanted this");
        addMsg(MessageType.FULFILL, "This is the best I can do");
        addMsg(MessageType.FULFILL, "Is this enough?");
        addMsg(MessageType.FULFILL, "How about this, do you think it'll do?");
        addMsg(MessageType.FULFILL, "Maybe you want this and don't know it yet.");
        addMsg(MessageType.FULFILL, "Applause please!");
        addMsg(MessageType.HELP_WISH, "Hold the lamp & speak your wish!");
        addMsg(MessageType.HELP_WISH, "Hold the lamp & speak thy wish!");
        addMsg(MessageType.HELP_WISH, "Hold that lamp & let me know your wish");
        addMsg(MessageType.HELP_WISH, "Tell me your wish");
        addMsg(MessageType.HELP_WISH, "How about a wish?");
        addMsg(MessageType.HELP_WISH, "I'd like to hear your wish, just hold the lamp.");
        addMsg(MessageType.HELP_WISH, "Hold the lamp while speaking.");
        addMsg(MessageType.HELP_WISH, "Bla bla bla.");
        addMsg(MessageType.HELP_WISH, "Speak your mind, release your wish.");
        addMsg(MessageType.HELP_WISH, "You only had to rub the lamp, but I appreciate you going the extra mile.");
        addMsg(MessageType.WISHES_LEFT, "You have WISHES wishes left.");
        addMsg(MessageType.WISHES_LEFT, "I can still grant WISHES wishes.");
        addMsg(MessageType.WISHES_LEFT, "WISHES wishes to go.");
        addMsg(MessageType.WISHES_LEFT, "How many wishes left? WISHES");
        addMsg(MessageType.WISHES_LEFT, "About WISHES wishes..");
        addMsg(MessageType.WISHES_LEFT, "You still got WISHES wishes left.");
        addMsg(MessageType.WISHES_LEFT, "I can grant you WISHES wishes.");
        addMsg(MessageType.WISHES_LEFT, "WISHES wishes left.");
        addMsg(MessageType.OUT_OF_WISHES, "No wishes left.");
        addMsg(MessageType.OUT_OF_WISHES, "This lamp has no wishes.");
        addMsg(MessageType.OUT_OF_WISHES, "Zero, Nada, Non' - Wishes left.");
        addMsg(MessageType.OUT_OF_WISHES, "I'm afraid it's my time to go kids, this lamp is out of wishes.");
        addMsg(MessageType.OUT_OF_WISHES, "You used all of your wishes.");
        addMsg(MessageType.OUT_OF_WISHES, "I'm all out of wishes.");
        addMsg(MessageType.OUT_OF_WISHES, "You're out of wishes.");
        addMsg(MessageType.OUT_OF_WISHES, "0 wishes left.");
        addMsg(MessageType.OUT_OF_WISHES, "That was your last wish.");
        addMsg(MessageType.OUT_OF_WISHES, "Excuse me, I am in the shower. I think you used your wishes already.");
        addMsg(MessageType.CANNOT_FIND, "Gadzooks! I don't think that can be done, have another try.");
        addMsg(MessageType.CANNOT_FIND, "Sorry, I do not know how to fulfill your wish.");
        addMsg(MessageType.CANNOT_FIND, "Sorry, I do not know how to fulfill that wish.");
        addMsg(MessageType.CANNOT_FIND, "Could you think of something less impossible?");
        addMsg(MessageType.CANNOT_FIND, "That may be beyond my capabilities");
        addMsg(MessageType.CANNOT_FIND, "Please ask for the dev to add that to my abilities. ");
    }

}
