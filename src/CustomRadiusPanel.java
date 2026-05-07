class CustomRadiusPanel extends JPanel {

    private int tl, tr, br, bl;

    public CustomRadiusPanel(int tl, int tr, int br, int bl) {
        this.tl = tl;
        this.tr = tr;
        this.br = br;
        this.bl = bl;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        Path2D path = new Path2D.Float();

        // Start top-left
        path.moveTo(tl, 0);

        // Top edge
        path.lineTo(w - tr, 0);
        path.quadTo(w, 0, w, tr);

        // Right edge
        path.lineTo(w, h - br);
        path.quadTo(w, h, w - br, h);

        // Bottom edge
        path.lineTo(bl, h);
        path.quadTo(0, h, 0, h - bl);

        // Left edge
        path.lineTo(0, tl);
        path.quadTo(0, 0, tl, 0);

        path.closePath();

        // Fill background
        g2.setColor(getBackground());
        g2.fill(path);

        g2.dispose();

        super.paintComponent(g);
    }
}