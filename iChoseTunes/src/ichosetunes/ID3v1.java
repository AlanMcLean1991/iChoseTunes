package ichosetunes;



//---ID3V1--------------------
//   TAG        |   3 chars   |
//   Song title |  30 chars   |
//   Artists    |  30 chars   |
//   Albulm     |  30 chars   |
//   Year       |   4 chars   |
//   Comment    |  30 chars   |
//   Genre      |   1 char    |
//----------------------------

import java.io.*;
/**
 *
 * @author Alan
 */
public class ID3v1 {
    private File file;
    private String songTitle;
    private String artist;
    private String album;
    private String year;
    private String comment;
    private String genre;

    public ID3v1(File musicFile) {
        // read id3v1 tag into member variables
        try {
            this.file = musicFile;
            RandomAccessFile raf = new RandomAccessFile(file, "r");
            String metadata = null;
            char[] tmp = null;

            raf.seek(raf.length()-128);
            metadata = raf.readLine();
            if(metadata.regionMatches(true, 0, "tag", 0, 3)) { // found id3v1 tag
                setID3v1TagVals(metadata);

            }

        } catch(FileNotFoundException e) {
            System.out.println("Error: Could not find file");
            e.printStackTrace();
        } catch(IOException e) {
            System.out.println("Error: Could not access file");
            e.printStackTrace();
        }
    }
    public final void setID3v1TagVals(String metadata) {
        // copy song title - 30 chars
        char[] tmp = new char[30];
        try {
            metadata.getChars(3, 33, tmp, 0);
        } catch(StringIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        setSongTitle(new String(tmp));
        //System.out.println("Song Title: " + this.getSongTitle());

        // copy Artists - 30 chars
        tmp = new char[30];
        try {
            metadata.getChars(33, 63, tmp, 0);
        } catch(StringIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        setArtist(new String(tmp));
        //System.out.println("Artist: " + this.getArtist());

        // copy Album - 30 chars
        tmp = new char[30];
        try {
            metadata.getChars(63, 93, tmp, 0);
        } catch(StringIndexOutOfBoundsException e) {
            //e.printStackTrace();
        }
        setAlbum(new String(tmp));
        //System.out.println("Album: " + this.getAlbum());

        // copy Year - 4 chars
        tmp = new char[4];
        try { 
            metadata.getChars(93, 97, tmp, 0);
        } catch(StringIndexOutOfBoundsException e) {
            //e.printStackTrace();
        }
        setYear(new String(tmp));
        //System.out.println("Year: " + this.getYear());

        // copy Comment - 30 chars
        tmp = new char[30];
        try {
            metadata.getChars(97, 127, tmp, 0);
        } catch(StringIndexOutOfBoundsException e) {
            //e.printStackTrace();
        }
        setComment(new String(tmp));
        //System.out.println("Comment: " + this.getComment());

        // copy Genre - 1 char
        tmp = new char[1];
        try {
            metadata.getChars(127, 128, tmp, 0);
        } catch(StringIndexOutOfBoundsException e) {
            //e.printStackTrace();
        }
        try {
            setGenre(Integer.parseInt(new String(tmp)));
        } catch(NumberFormatException e) {
            setGenre("");
        }
        //System.out.println("Genre: " + this.getGenre());
    }
    public String getSongTitle() {
        return this.songTitle;
    }
    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }
    public String getArtist() {
        return this.artist;
    }
    public void setArtist(String artist) {
        this.artist = artist;
    }
    public String getAlbum() {
        return this.album;
    }
    public void setAlbum(String album) {
        this.album = album;
    }
    public String getYear() {
        return this.year;
    }
    public void setYear(String year) {
        this.year = year;
    }
    public String getComment() {
        return this.comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getGenre() {
        return this.genre;
    }
    public void setGenre(int val) {
        String genres[] = {"Blues", "Classic Rock", "Country", "Dance", "Disco",
                            "Funk", "Grunge", "Hip-Hop", "Jazz", "Metal", "New Age",
                            "Oldies", "Other", "Pop", "R&B", "Rap", "Reggae", "Rock",
                            "Techno", "Industrial", "Alternative",  "Ska", "Death Metal",
                            "Pranks", "Soundtrack", "Euro-Techno", "Ambient", "Trip-Hop",
                            "Vocal", "Jazz+Funk", "Fusion", "Trance", "Classical", "Instrumental",
                            "Acid", "House", "Game", "Sound Clip", "Gospel", "Noise", "AlternRock",
                            "Bass", "Soul", "Punk", "Space", "Meditative", "Instrumental Pop",
                            "Instrumental Rock", "Ethnic", "Gothic", "Darkwave", "Techno-Industrial",
                            "Electronic", "Pop-Folk", "Eurodance", "Dream", "Southern Rock", "Comedy",
                            "Cult", "Gangsta", "Top 40", "Christian Rap", "Pop/Funk", "Jungle",
                            "Native American", "Cabaret", "New Wave", "Psychadelic", "Rave", "Showtunes",
                            "Trailer", "Lo-Fi", "Tribal", "Acid Punk", "Acid Jazz", "Polka", "Retro",
                            "Musical", "Rock & Roll", "Hard Rock" };

        this.genre = genres[val];
    }
    public void setGenre(String str) {
        this.genre = str;
    }
}
