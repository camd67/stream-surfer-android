package com.streamsurfer.surfers.streamsurfer;

public class ListEntry {
    private String title;
    private ShowStatus status;
    private int epsSeen;
    private int totalEps;
    private int rating;
    private String filename;
    public static final int MAX_RATING = 5;

    public ListEntry(String title, ShowStatus status, int epsSeen, int totalEps, int rating, String filename) {
        if (title == null || status == null || filename == null) {
            throw new IllegalArgumentException("Title, filename, or status cannot be null");
        }
        if (epsSeen < 0 || totalEps < 0 || rating < 0) {
            throw new IllegalArgumentException("Cannot have negative episode counts or rating");
        }

        this.title = title;
        this.status = status;
        this.epsSeen = epsSeen;
        this.totalEps = totalEps;
        this.rating = rating;

        if(status == ShowStatus.COMPLETE){
            this.epsSeen = this.totalEps;
        }
        constrainEps();
        constrainRating();
    }

    public void setRating(int rating) {
        this.rating = rating;
        constrainRating();
    }

    private void constrainRating() {
        if (rating > MAX_RATING) {
            rating = MAX_RATING;
        }
    }

    public void incrementEps() {
        epsSeen++;
        constrainEps();
        if(epsSeen == totalEps){
            setStatus(ShowStatus.COMPLETE);
        }
    }

    public void setEpsSeen(int epsSeen) {
        this.epsSeen = epsSeen;
        constrainEps();
    }

    private void constrainEps() {
        if (epsSeen > totalEps) {
            epsSeen = totalEps;
        }
    }

    public void setStatus(ShowStatus newStatus) {
        status = newStatus;
        if (status == ShowStatus.COMPLETE) {
            // if the show is complete, assume all eps watched
            epsSeen = totalEps;
        }
    }

    public String getFilename() {
        return filename;
    }

    public String getTitle() {
        return title;
    }

    public ShowStatus getStatus() {
        return status;
    }

    public int getEpsSeen() {
        return epsSeen;
    }

    public int getTotalEps() {
        return totalEps;
    }

    public int getRating() {
        return rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListEntry listEntry = (ListEntry) o;

        if (getEpsSeen() != listEntry.getEpsSeen()) return false;
        if (getTotalEps() != listEntry.getTotalEps()) return false;
        if (getRating() != listEntry.getRating()) return false;
        if (!getTitle().equals(listEntry.getTitle())) return false;
        return getStatus() == listEntry.getStatus();

    }

    @Override
    public int hashCode() {
        int result = getTitle().hashCode();
        result = 31 * result + getStatus().hashCode();
        result = 31 * result + getEpsSeen();
        result = 31 * result + getTotalEps();
        result = 31 * result + getRating();
        return result;
    }

    @Override
    public String toString() {
        return "ListEntry{" +
                "title='" + title + '\'' +
                ", status=" + status +
                ", epsSeen=" + epsSeen +
                ", totalEps=" + totalEps +
                ", rating=" + rating +
                '}';
    }
}

