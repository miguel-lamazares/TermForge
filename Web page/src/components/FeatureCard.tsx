interface FeatureCardProps {
  icon: string;
  title: string;
  description: string;
}

export const FeatureCard = ({ icon, title, description }: FeatureCardProps) => {
  return (
    <div className="group relative rounded-xl border border-border bg-card/60 backdrop-blur-sm p-6 transition-all duration-300 hover:border-primary/50 hover:-translate-y-1 hover:shadow-terminal">
      <div className="absolute inset-0 rounded-xl bg-gradient-soft opacity-0 group-hover:opacity-100 transition-opacity duration-300 pointer-events-none" />
      <div className="relative">
        <div className="text-3xl mb-3">{icon}</div>
        <h3 className="font-mono font-semibold text-lg mb-2 text-foreground">
          {title}
        </h3>
        <p className="text-sm text-muted-foreground leading-relaxed">
          {description}
        </p>
      </div>
    </div>
  );
};
