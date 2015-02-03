package prototyped.schedulr.activity;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import prototyped.schedulr.R;

public class FragmentHelpSlide extends Fragment
{
    private static final String VIEW_PAGER_POSITION = "position";
    private static Context context;

    public static final FragmentHelpSlide newInstance(Context c, int position)
    {
        context = c;
        FragmentHelpSlide fragmentHelpSlide = new FragmentHelpSlide();
        Bundle args = new Bundle();
        args.putInt(VIEW_PAGER_POSITION, position);
        fragmentHelpSlide.setArguments(args);

        return fragmentHelpSlide;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_help_slide, container, false);
        TextView textView = (TextView)rootView.findViewById(R.id.textView_fragment_help_slide);
        textView.setText(Integer.toString(getArguments().getInt(VIEW_PAGER_POSITION)));

        return rootView;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);

        ((ActivityMain)activity).onSectionAttached(getArguments().getInt(VIEW_PAGER_POSITION));
    }

    public void onResume()
    {
        super.onResume();
    }

    public void onPause()
    {
        super.onPause();
    }
}
