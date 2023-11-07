package cz.cvut.fit.anteater.model.value;

import lombok.NonNull;
import lombok.Value;

@Value
public class TextFeature {
	@NonNull private String title;
	@NonNull private String text;
}
