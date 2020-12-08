package net.mz.rb.subdiffusion;

class Point {
    private int x, y, borderTop, borderLeft;
    private double timeOfWalking;
    private boolean isEnded;

    Point(int bTop, int bLeft) {
        x = 0;
        y = 0;
        borderTop = bTop;
        borderLeft = bLeft;
        timeOfWalking = 0.0;
        isEnded = false;
    }

    void walk() {
        if (Functions.willPointWalk()) {
            int a = Functions.getLengthOfVector();
            int b = Functions.getSomethingOfVector();
            switch (a) {
                case 1: {
                    switch (b) {
                        case 1:
                            x++;
                            break;
                        case 2:
                            y++;
                            break;
                        case 3:
                            x--;
                            break;
                        case 4:
                            y--;
                            break;
                    }
                }
                break;
                case 2: {
                    switch (b) {
                        case 1: {
                            x++;
                            y++;
                        }
                        break;
                        case 2: {
                            x++;
                            y--;
                        }
                        break;
                        case 3: {
                            x--;
                            y++;
                        }
                        break;
                        case 4: {
                            x--;
                            y--;
                        }
                        break;
                    }
                }
                break;
                case 3: {
                    switch (b) {
                        case 1:
                            x += 2;
                            break;
                        case 2:
                            y += 2;
                            break;
                        case 3:
                            x -= 2;
                            break;
                        case 4:
                            y -= 2;
                            break;
                    }
                }
                break;
            }
            if (Math.abs(x) > borderLeft || Math.abs(y) > borderTop) {
                switch (a) {
                    case 1: {
                        switch (b) {
                            case 1:
                                x--;
                                break;
                            case 2:
                                y--;
                                break;
                            case 3:
                                x++;
                                break;
                            case 4:
                                y++;
                                break;
                        }
                    }
                    break;
                    case 2: {
                        switch (b) {
                            case 1: {
                                x--;
                                y--;
                            }
                            break;
                            case 2: {
                                x--;
                                y++;
                            }
                            break;
                            case 3: {
                                x++;
                                y--;
                            }
                            break;
                            case 4: {
                                x++;
                                y++;
                            }
                            break;
                        }
                    }
                    break;
                    case 3: {
                        switch (b) {
                            case 1:
                                x -= 2;
                                break;
                            case 2:
                                y -= 2;
                                break;
                            case 3:
                                x += 2;
                                break;
                            case 4:
                                y += 2;
                                break;
                        }
                    }
                    break;
                }
            }
            //timeOfWalking += Functions.getNextMoment();
            //if (timeOfWalking > Functions.T) isEnded = true;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isEnded() {
        return isEnded;
    }
}
