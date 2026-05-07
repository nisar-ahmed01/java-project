class RoundScrollPane extends JScrollPane {

    private int radius;

    public RoundScrollPane(Component view , int radius) {
        super(view);
        this.radius = radius;

        setOpaque(false);
        getViewport().setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        g2.dispose();

        super.paintComponent(g);
    }
}

public