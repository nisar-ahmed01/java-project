class RoundedPasswordField extends JPasswordField{

    private int radius;
    private String placeholder;
    private boolean focused = false;

    public RoundedPasswordField(String placeholder, int radius) {
        this.placeholder = placeholder;
        this.radius = radius;

        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // Focus effect (animated border trigger)
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                focused = true;
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                focused = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // background
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        super.paintComponent(g);
        g2.dispose();

        // placeholder text
        if (getText().isEmpty() && !focused) {
            Graphics2D g3 = (Graphics2D) g.create();
            g3.setColor(Color.GRAY);
            g3.setFont(getFont().deriveFont(Font.PLAIN));
            g3.drawString(placeholder, 12, getHeight() / 2 + 5);
            g3.dispose();
        }
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // Animated border effect
        if (focused) {
            g2.setColor(new Color(142, 83, 233)); // purple glow
        } else {
            g2.setColor(new Color(200, 180, 240)); // normal border
        }

        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);

        g2.dispose();
    }
}